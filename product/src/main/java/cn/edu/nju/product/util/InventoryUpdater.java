package cn.edu.nju.product.util;

import cn.edu.nju.product.dao.ProductRepository;
import cn.edu.nju.product.feign.InventoryFeign;
import entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * 从product表中查到所有product
 * 拿id和num去更新库存表
 *
 * 需要feign
 */
// implements CommandLineRunner
@Component
public class InventoryUpdater {
    private ProductRepository productRepository;
    private InventoryFeign inventoryFeign;

    @Autowired
    public InventoryUpdater(ProductRepository productRepository, InventoryFeign inventoryFeign) {
        this.productRepository = productRepository;
        this.inventoryFeign = inventoryFeign;
    }

//    @Override
//    public void run(String... args) throws Exception {
//        updateInventory();
//    }

    @Transactional
    public void updateInventory() {
        for(int page = 0;page < 12;page++) {
            Pageable pageable = PageRequest.of(page, 969, Sort.Direction.ASC, "id");
            Page<Product> productPage = productRepository.findAll(pageable);
            for (Product product : productPage) {
                Long productId = product.getId();
                boolean result = inventoryFeign.addInventory(productId, new Random().nextInt(30));
                if (!result) {
                    throw new RuntimeException("更新库存失败，全部回滚");
                }
            }
        }
    }

}
