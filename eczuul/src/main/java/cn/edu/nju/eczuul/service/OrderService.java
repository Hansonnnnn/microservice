package cn.edu.nju.eczuul.service;

import cn.edu.nju.eczuul.entity.SecGood;
import entity.order.Order;
import entity.user.User;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrder(User user, SecGood secGood);
}
