1. 客户端携带请求参数数据量太大，客户端会报400
```java
    @GetMapping("/inventories")
    Map<Long, Integer> inventories(@RequestParam("pids") List<Long> pids);

    Map<Long, Integer> idAndInventory = inventoryFeign.inventories(productIDs);
    
    exception message: "status 400 reading InventoryFeign#inventories(List)"
```


