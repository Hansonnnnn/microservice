package cn.edu.nju.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("inventory")
public interface InventoryFeign {

    @PostMapping("/inventories/add")
    boolean addInventory(@RequestParam("pid") Long pid,
                            @RequestParam("num") int num);

    @GetMapping("/inventory/{pid}")
    int inventory(@PathVariable("pid")Long pid);
}
