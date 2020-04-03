package entity.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * jpa/Hibernate创建order表出错
 * https://oomake.com/question/827374
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private BigDecimal price;
    @Column(length = 32)
    private String address;
    @Column
    private OrderStatus status;
    @Column
    private Date createTime;
    @Column
    private Date updateTime;
}
