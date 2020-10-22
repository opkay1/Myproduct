
package rentalService.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="Product", url="${api.product.url}")
//@FeignClient(name="Product", url="http://localhost:8081")
public interface ProductService {

    @RequestMapping(method= RequestMethod.POST, path="/products")
    public void save(@RequestBody Product product);

}