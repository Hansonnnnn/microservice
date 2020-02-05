package cn.edu.nju.product;

import cn.edu.nju.product.aop.timing.ExecuteTime;
import cn.edu.nju.product.dao.ProductRepository;
import cn.edu.nju.product.feign.InventoryFeign;
import entity.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {
    @Autowired
    private InventoryFeign inventoryFeign;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFeign() {
        inventoryFeign.addInventory(11630L, 1);
        System.out.println();
    }

    /**
     * 为什么这么调就不行
     */
    @Test
    public void testTransaction() {
//        updateProduct();
    }

    @Test
    @Transactional
    public void updateProduct() {
        for (int i = 0;i < 3;i++) {
            if (i==1) {
                throw new RuntimeException("故意中断");
            }
            Product product = new Product();
            product.setProductId(1111111111111111L);
            product.setPrice(1239012321.123);
            product.setBrandName("Nike");
            product.setName("Shoe");
            productRepository.save(product);
        }
    }

    @Test
    public void testAOP() {
        test();
    }

    @ExecuteTime
    public void test() {
        for (int i = 0;i < 100000000;i++) {
        }
    }
}
