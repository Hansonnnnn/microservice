package cn.edu.nju.inventory;

import cn.edu.nju.inventory.dao.InventoryRepository;
import entity.inventory.Inventory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryApplicationTests {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void addInventories() {
        try {
            Inventory inventory = new Inventory();
            inventory.setPid(4L);
            inventory.setAvailable(100);
            inventoryRepository.save(inventory);
        } catch (Throwable e) {
            System.out.println("message: " + e.getMessage());
            System.out.println("cause: " + e.getCause());
            e.printStackTrace();
        }
    }


}
