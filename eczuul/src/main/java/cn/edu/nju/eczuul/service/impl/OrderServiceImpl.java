package cn.edu.nju.eczuul.service.impl;

import cn.edu.nju.eczuul.common.Const;
import cn.edu.nju.eczuul.dao.OrderRepository;
import cn.edu.nju.eczuul.dao.SecGoodsOrderRepository;
import cn.edu.nju.eczuul.entity.SecGood;
import cn.edu.nju.eczuul.entity.SecGoodsOrder;
import cn.edu.nju.eczuul.redis.SecKillKey;
import cn.edu.nju.eczuul.service.GoodsService;
import cn.edu.nju.eczuul.service.OrderService;
import cn.edu.nju.eczuul.service.RedisUtil;
import entity.order.Order;
import entity.order.OrderStatus;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    private GoodsService goodsService;
    private OrderRepository orderRepository;
    private SecGoodsOrderRepository secGoodsOrderRepository;

    private RedisUtil redisUtil;

    @Autowired
    public OrderServiceImpl(GoodsService goodsService,
                            OrderRepository orderRepository,
                            SecGoodsOrderRepository secGoodsOrderRepository,
                            RedisUtil redisUtil) {
        this.goodsService = goodsService;
        this.orderRepository = orderRepository;
        this.secGoodsOrderRepository = secGoodsOrderRepository;
        this.redisUtil = redisUtil;
    }

    @Override
    public Order createOrder(User user, SecGood secGood) {
        int success = goodsService.reduceStock(secGood.getId());
        if (success == 1) {
            Order order = Order.builder().pid(secGood.getId())
                    .uid(user.getId())
                    .address(user.getAddresses().iterator().next())
                    .createTime(new Date())
                    .updateTime(new Date())
                    .num(1)
                    .price(secGood.getSecPrice())
                    .status(OrderStatus.UNCONFIRMED)
                    .build();
            order = orderRepository.save(order);
            SecGoodsOrder secGoodsOrder = SecGoodsOrder.builder()
                    .goodsId(secGood.getId())
                    .orderId(order.getOid())
                    .userId(user.getId())
                    .build();
            secGoodsOrderRepository.save(secGoodsOrder);
            return order;
        } else {
            setSecGoodsOver(secGood.getId());
            return null;
        }
    }

    /**
     * 设置缓存该商品以秒杀完
     * @param goodsId
     */
    private void setSecGoodsOver(Long goodsId) {
        redisUtil.set(SecKillKey.isGoodsOver, goodsId.toString(), true, Const.RedisCacheExpireTime.GOODS_ID);
    }

    private boolean exist(Long goodsId) {
        return redisUtil.hasKey(SecKillKey.isGoodsOver, goodsId.toString());
    }
}
