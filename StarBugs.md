1. 客户端携带请求参数数据量太大，客户端会报400
```java
    @GetMapping("/inventories")
    Map<Long, Integer> inventories(@RequestParam("pids") List<Long> pids);

    Map<Long, Integer> idAndInventory = inventoryFeign.inventories(productIDs);
    
    exception message: "status 400 reading InventoryFeign#inventories(List)"
```

2. Spring AOP的正确使用姿势
切点PointCut只能切在Rest的接口上，比如下面

```java
//这个切点对于加了@GetMapping的方法就可以切住，不加就切不住
@Pointcut("execution(* cn.edu.nju.user.util.UserGenerator.generateUsers(..))")
public void generateUser(){}

//切得住
@GetMapping("/user/generate2")
public void generateUsers() throws IOException {

//切不住        
public void generateUsers() throws IOException {
}
```



