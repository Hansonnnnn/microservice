package cn.edu.nju.eczuul.dao;

import cn.edu.nju.eczuul.entity.SecGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SecGoodsRepository extends JpaRepository<SecGood, Long> {
    @Transactional
    @Modifying
    @Query("update SecGood sg set sg.stockCount = sg.stockCount - 1 where sg.id=?1")
    int reduceStock(Long goodsId);
}
