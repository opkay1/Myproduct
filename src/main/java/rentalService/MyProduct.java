package rentalService;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="MyProduct_table")
public class MyProduct {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer qty;

    @PostPersist
    public void onPostPersist(){
        //MyproductSaved
        MyproductSaved myproductSaved = new MyproductSaved();
        BeanUtils.copyProperties(this, myproductSaved);
        myproductSaved.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.
        // 동기호출
        rentalService.external.Product product = new rentalService.external.Product();
        // mappings goes here
        BeanUtils.copyProperties(this, product);
        //product.setName(deliveryId);
        //product.setRentalId(this.id);
        //delivery.setStatus("CANCELED");
        //aa.external.Product product = new aa.external.Product();
        // mappings goes here
        product.setStatus("SAVED");
        MyProductApplication.applicationContext.getBean(rentalService.external.ProductService.class)
            .save(product);


    }

    @PreRemove
    public void onPreRemove(){

        MyproductDeleted myproductDeleted = new MyproductDeleted();
        BeanUtils.copyProperties(this, myproductDeleted);
        myproductDeleted.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }




}
