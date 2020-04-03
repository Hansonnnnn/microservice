package cn.edu.nju.eczuul.dao;

import cn.edu.nju.eczuul.entity.SecGoodsOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecGoodsOrderRepository extends JpaRepository<SecGoodsOrder, Long> {
    SecGoodsOrder findByGoodsIdAndAndUserId(Long goodsId, Long userId);
}
