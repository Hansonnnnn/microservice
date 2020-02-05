package cn.edu.nju.inventory.rest;

import cn.edu.nju.inventory.dao.InventoryRepository;
import entity.inventory.Inventory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "库存服务", description = "提供库存服务相关Rest API")
@RestController
public class InventoryController {
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * 更新库存API
     * @param pid 商品
     * @param num 减少或增加的商品数量
     * @return 是否更新成功
     */
    @ApiOperation(value = "更新库存", notes = "更新数量为正则减少对应数量的库存，为负则增加对应数量的库存")
    @PostMapping("/inventories/{pid}")
    public boolean updateInventories(@PathVariable("pid")Long pid,
                              @RequestParam("num")int num) {
        try {
            Inventory inventory = inventoryRepository.getOne(pid);
            if (num > 0 && inventory.getAvailable() < num) {return false;}
            inventory.setAvailable(inventory.getAvailable() - num);
            inventory.setOrderOccupied(inventory.getOrderOccupied() + num);
            inventoryRepository.save(inventory);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 之所以将增加库存API和更新库存（增/删）API分开，是因为更新库存API为高频调用接口
     * 本接口为低频调用接口
     * @param pid 商品id
     * @param num 正数，传入负数更新失败
     * @return 是否添加成功
     */
    @ApiOperation("增加库存")
    @PostMapping("/inventories/add")
    public boolean addInventories(@RequestParam("pid")Long pid,
                                  @RequestParam("num")int num) {
        try {
            Inventory inventory = new Inventory();
            inventory.setPid(pid);
            inventory.setAvailable(num);
            inventoryRepository.save(inventory);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    @ApiOperation("查看库存")
    @GetMapping("/inventory/{pid}")
    public int inventory(@PathVariable("pid")Long pid) {
        try {
            Inventory inventory = inventoryRepository.getOne(pid);
            return inventory.getAvailable();
        } catch (EntityNotFoundException e) {
            return -1;
        }
    }

    @GetMapping("/inventories")
    public Map<Long, Integer> inventories(@RequestParam("pids") List<Long> pids) {
        Map<Long, Integer> idAndInventory = new HashMap<>();
        for (Long pid : pids) {
            try {
                Inventory inventory = inventoryRepository.getOne(pid);
                idAndInventory.put(pid, inventory.getAvailable());
            } catch (EntityNotFoundException e) {
                idAndInventory.put(pid, -1);
            }
        }
        return idAndInventory;
    }
}
