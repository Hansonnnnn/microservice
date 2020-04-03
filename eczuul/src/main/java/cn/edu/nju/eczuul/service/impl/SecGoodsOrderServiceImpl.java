package cn.edu.nju.eczuul.service.impl;

import cn.edu.nju.eczuul.dao.SecGoodsOrderRepository;
import cn.edu.nju.eczuul.entity.SecGoodsOrder;
import cn.edu.nju.eczuul.service.SecGoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecGoodsOrderServiceImpl implements SecGoodsOrderService {
    private SecGoodsOrderRepository secGoodsOrderRepository;

    @Autowired
    public SecGoodsOrderServiceImpl(SecGoodsOrderRepository secGoodsOrderRepository) {
        this.secGoodsOrderRepository = secGoodsOrderRepository;
    }

    @Override
    public SecGoodsOrder getSecGoodsOrderById(Long id) {
        return secGoodsOrderRepository.getOne(id);
    }

    @Override
    public SecGoodsOrder getSecGoodsOrderByGoodsIdAndUserId(Long goodsId, Long userId) {
        return secGoodsOrderRepository.findByGoodsIdAndAndUserId(goodsId, userId);
    }
}
