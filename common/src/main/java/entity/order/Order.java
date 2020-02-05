package entity.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * jpa/Hibernate创建order表出错
 * https://oomake.com/question/827374
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue
    private Long oid;
    @Column(nullable = false)
    private Long uid;
    @Column(nullable = false)
    private Long pid;
    @Column
    private int num;
    @Column
    private double price;
    @Column(length = 32)
    private String address;
    @Column
    private OrderStatus status;
    @Column
    private Long createTime;
    @Column
    private Long updateTime;
}
