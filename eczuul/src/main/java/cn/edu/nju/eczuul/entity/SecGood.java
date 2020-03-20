package cn.edu.nju.eczuul.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SecGood {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String detail;
    private BigDecimal price;
    private BigDecimal secPrice;
    private String url;
    @ApiModelProperty("秒杀开始时间，格式：yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiModelProperty("秒杀结束时间，格式：yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer stockCount;
}
