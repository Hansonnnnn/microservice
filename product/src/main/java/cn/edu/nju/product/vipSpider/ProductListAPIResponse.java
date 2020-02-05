package cn.edu.nju.product.vipSpider;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductListAPIResponse {
    private int code;
    private String msg;
    private VIPData data;

    @Data
    @NoArgsConstructor
    class VIPData {
        private String abtestId;
        private int batchSize;
        private int isLast;
        private int keepTime;
        private int pageSize;
        private List<VIPProduct> products;
        private int total;
    }

    @Data
    @NoArgsConstructor
    class VIPProduct {
        private String pid;
    }
}
