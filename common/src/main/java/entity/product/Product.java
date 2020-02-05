package entity.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long productId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double price;
    @Column
    private String brandName;
}
