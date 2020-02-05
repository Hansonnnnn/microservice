package cn.edu.nju.order.rest;

import cn.edu.nju.order.dao.OrderRepository;
import cn.edu.nju.order.feign.InventoryFeign;
import cn.edu.nju.order.feign.ProductFeign;
import entity.order.Order;
import entity.order.OrderStatus;
import entity.product.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "订单服务", description = "提供订单服务相关Rest API")
@RestController
public class OrderController {
    private ProductFeign productFeign;
    private InventoryFeign inventoryFeign;
    private OrderRepository orderRepository;


    @Autowired
    public OrderController(ProductFeign productFeign, InventoryFeign inventoryFeign, OrderRepository orderRepository) {
        this.productFeign = productFeign;
        this.inventoryFeign = inventoryFeign;
        this.orderRepository = orderRepository;
    }

    @ApiOperation("生成订单接口")
    @PostMapping("/orders")
    public boolean createOrder(@RequestParam("uid")Long uid,
                             @RequestParam("pid")Long pid,
                             @RequestParam("num")int num,
                             @RequestParam("address")String address) {
        Order order = new Order();
        order.setUid(uid);
        order.setPid(pid);
        order.setAddress(address);
        order.setNum(num);
        //price = num * product's price
        Product product = productFeign.getProductById(pid);
        order.setPrice(num * product.getPrice());
        order.setCreateTime(new Date().getTime());
        order.setStatus(OrderStatus.UNCONFIRMED);

        orderRepository.save(order);

        //查库存，如果库存大于num，则下单成功，更新订单状态；小于num，下单失败，订单状态改为失效状态
        if (inventoryFeign.updateInventory(pid, num)) {
            order.setStatus(OrderStatus.CONFIRMED);
            order.setUpdateTime(new Date().getTime());
            orderRepository.save(order);
            return true;
        } else {
            order.setStatus(OrderStatus.INVALID);
            order.setUpdateTime(new Date().getTime());
            orderRepository.save(order);
            return false;
        }
    }

    @ApiOperation("查询订单接口")
    @GetMapping("/orders")
    public List<Order> queryOrders(@RequestParam("uid")Long uid) {
        List<Order> orders = orderRepository.findAllByUid(uid);
        if (orders == null || orders.isEmpty()) {
            return new ArrayList<>();
        }
        return orders;
    }

    @ApiOperation("查询某个订单接口")
    @GetMapping("/orders/{id}")
    public Order queryOrder(@PathVariable("id")Long id) {
        return orderRepository.getOne(id);
    }
}
