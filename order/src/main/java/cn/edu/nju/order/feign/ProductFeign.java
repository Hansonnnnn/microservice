package cn.edu.nju.order.feign;

import entity.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product")
public interface ProductFeign {
    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id")Long id);
}
