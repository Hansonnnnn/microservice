package cn.edu.nju.eczuul.service;

import cn.edu.nju.eczuul.entity.SecGood;

import java.util.List;

public interface GoodsService {
    List<SecGood> getSecGoods();

    SecGood getSecGoodById(Long id);

    int reduceStock(Long id);
}
