package cn.edu.nju.eczuul.service;

import cn.edu.nju.eczuul.entity.SecGoodsOrder;

public interface SecGoodsOrderService {
    SecGoodsOrder getSecGoodsOrderById(Long id);

    SecGoodsOrder getSecGoodsOrderByGoodsIdAndUserId(Long goodsId, Long userId);
}
