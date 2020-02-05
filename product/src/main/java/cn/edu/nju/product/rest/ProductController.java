package cn.edu.nju.product.rest;

import cn.edu.nju.product.dao.ProductRepository;
import cn.edu.nju.product.feign.InventoryFeign;
import cn.edu.nju.product.util.timing.ExecuteTime;
import entity.product.Product;
import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "商品服务", description = "提供商品服务相关Rest API")
@RestController
public class ProductController {
    private ProductRepository productRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ApiOperation("通过商品id查询商品")
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id")Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new RuntimeException("查询商品出错，没有对应id的商品");
    }

    @ApiOperation("通过商品名称查询商品接口")
    @GetMapping("/products")
    public List<Product> findProducts(@RequestParam("name") String name) {
        return productRepository.findProductsByNameContains(name);
    }

    @ApiOperation("添加商品接口")
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct = productRepository.save(newProduct);
        return newProduct;
    }
}
