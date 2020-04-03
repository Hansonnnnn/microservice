package cn.edu.nju.eczuul.service.impl;

import cn.edu.nju.eczuul.dao.SecGoodsRepository;
import cn.edu.nju.eczuul.entity.SecGood;
import cn.edu.nju.eczuul.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    private SecGoodsRepository secGoodsRepository;

    @Autowired
    public GoodsServiceImpl(SecGoodsRepository secGoodsRepository) {
        this.secGoodsRepository = secGoodsRepository;
    }

    @Override
    public List<SecGood> getSecGoods() {
        return secGoodsRepository.findAll();
    }

    @Override
    public SecGood getSecGoodById(Long id) {
        return secGoodsRepository.getOne(id);
    }

    @Override
    public int reduceStock(Long id) {
        return secGoodsRepository.reduceStock(id);
    }
}
