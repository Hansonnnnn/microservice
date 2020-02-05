package cn.edu.nju.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("inventory")
public interface InventoryFeign {

    @PostMapping("/inventories/{pid}")
    boolean updateInventory(@PathVariable("pid")Long pid,
                         @RequestParam("num")int num);
}
