## 电商秒杀系统
e-commerce seckill system
 
### authserver module
服务授权模块

### eceureka module
电商微服务注册中心，基于Spring Cloud Netflix Eureka

### eczuul module
电商微服务网关，基于Spring Cloud Netflix Zuul

### inventory module
电商库存模块

### order module
电商订单模块

### product module
电商产品模块

### user module
电商用户模块


### 启动项目顺序
1. 启动eceureka模块
2. 启动Product、User、Order、Inventory模块
3. 启动eczuul模块