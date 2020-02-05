package cn.edu.nju.order;

import cn.edu.nju.order.feign.ProductFeign;
import entity.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Autowired
    private ProductFeign productFeign;

    @Test
    public void testProductFeign() {
        Product product = productFeign.getProductById(4L);
        if (product != null) {
            System.out.println(product.toString());
        }
    }
}
