package cn.edu.nju.eczuul.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SecGoodsOrder {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long goodsId;
    @Column(nullable = false)
    private Long orderId;
}
