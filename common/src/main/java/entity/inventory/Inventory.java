package entity.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 库存类设计采用简单的结构：分为：可销售库存、订单占用库存
 * 之间的流转关系：
 * 1.新建商品时，同步更新可销售库存，其他均为0，也可以同时修改几项指标
 * 2.生成订单时，可销售库存 -= 下单数量；订单占用数量 += 下单数量；
 * 3.取消订单时，订单占用库存 -= 下单数量；可销售库存 += 下单数量；
 */
@Data
@NoArgsConstructor
@Entity
public class Inventory {
    @Id
    private Long pid;
    @Column(columnDefinition = "int default 0")
    private int available;
    @Column(columnDefinition = "int default 0")
    private int orderOccupied;
}
