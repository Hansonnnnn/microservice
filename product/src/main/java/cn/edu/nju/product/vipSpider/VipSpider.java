package cn.edu.nju.product.vipSpider;

import cn.edu.nju.product.dao.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Random;

/**
 * 唯品会电商数据录入数据库
 * https://mapi.vip.com/vips-mobile/rest/shopping/pc/product/list/rank/rule/v2?callback=getProductIdsListRank&app_name=shop_pc&app_version=1&warehouse=VIP_SH&fdc_area_id=103102101&client=pc&mobile_platform=1&province_id=103102&api_key=70f71280d5d547b2a7bb370a529aeea1&user_id=&mars_cid=1576835618863_ebc6032611a71a494dcac3fe729be9c5&wap_consumer=a&uid=&abtestId=1872&mtmsRuleId=52179354&scene=rule_stream&sizeNames=&props=&vipService=&bigSaleTagIds=&filterStock=0&brandStoreSns=&sort=0&pageOffset=0&salePlatform=1&_=1576843888961
 * pageOffset
 */
//@Component
//    implements CommandLineRunner
public class VipSpider  {
    private static RestTemplate restTemplate = new RestTemplate();

    private ProductRepository productRepository;

    @Autowired
    public VipSpider (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Override
//    public void run(String... args) throws Exception {
//        restTemplate.getMessageConverters().add(new VIPHttpMessageConverter());
//        crawl();
//    }

    @Transactional
    public void crawl() {
        String url;
        for (int pageOffset = 10;pageOffset < 100;pageOffset++) {
            url = "https://mapi.vip.com/vips-mobile/rest/shopping/pc/product/list/rank/rule/v2?" +
                    "callback=getProductIdsListRank&app_name=shop_pc&app_version=1&warehouse=VIP_SH" +
                    "&fdc_area_id=103102101&client=pc&mobile_platform=1&province_id=103102&api_key=70f71280d5d547b2a7bb370a529aeea1" +
                    "&user_id=&mars_cid=1576835618863_ebc6032611a71a494dcac3fe729be9c5&wap_consumer=a&uid=&abtestId=1872" +
                    "&mtmsRuleId=52179354&scene=rule_stream&sizeNames=&props=&vipService=&bigSaleTagIds=&filterStock=0" +
                    "&brandStoreSns=&sort=0" +
                    "&pageOffset=" + pageOffset +
                    "&salePlatform=1&_=1576843888961";
            String pidsResponse = restTemplate.getForObject(url, String.class);
            if (pidsResponse != null) {
                String[] pids = pids(pidsResponse);
                if (pids != null && pids.length != 0) {
                    for (String pid : pids) {
                        sleepSomeTime();
                        String productUrl = "https://mapi.vip.com/vips-mobile/rest/shopping/pc/product/detail/v5" +
                                "?callback=detailInfoCB&productId=" + pid +
                                "&warehouse=VIP_SH&client_type=pc&fdc_area_id=103102101&brandid=1710613194&api_key=70f71280d5d547b2a7bb370a529aeea1" +
                                "&app_name=shop_pc&app_version=4&device=1&mars_cid=1576835618863_ebc6032611a71a494dcac3fe729be9c5&source_app=pc" +
                                "&mobile_platform=1&functions=brand_store_info%2CnewBrandLogo&highlightBgImgVer=1";
                        String productResponse = restTemplate.getForObject(productUrl, String.class);
                        if (productResponse == null || productResponse.isEmpty()) {
                            throw new RuntimeException("product api response is empty.");
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            JsonNode rootNode = objectMapper.readTree(productResponse.substring(13));
                            JsonNode dataNode = rootNode.path("data");
                            JsonNode productNode = dataNode.path("product");
                            long productId = productNode.get("productId").asLong();
                            double price = productNode.get("marketPrice").asDouble();
                            String name = productNode.get("longTitle").asText();
                            String brandName = productNode.get("brandStoreName").asText();
                            System.out.println(productId + " " + price + " " + name + " " + brandName);
                            Product product = new Product();
                            product.setProductId(productId);
                            product.setName(name);
                            product.setPrice(price);
                            product.setBrandName(brandName);
                            productRepository.save(product);
                        } catch (IOException e) {
                            throw new RuntimeException("crawl process failed.");
                        }
                    }
                }
            } else {
                System.out.println("response is null");
            }

        }
    }

    private static String[] pids(String response) {
        if (response.isEmpty()) {return null;}
        int startIndex = response.indexOf("[");
        int endIndex = response.lastIndexOf("]");
        String[] pidKV = response.substring(startIndex+1,endIndex).split(",");
        String[] pids = new String[pidKV.length];
        for (int i = 0;i < pidKV.length;i++) {
            String temp = pidKV[i].split(":")[1];
            pids[i] = temp.substring(1, temp.length()-2);
        }
        return pids;
    }

    private void sleepSomeTime() {
        int second = new Random().nextInt(10);
        try {
            Thread.sleep(second * 100);
        } catch (InterruptedException e) {
            throw new RuntimeException("thread sleep interrupted by some reasons");
        }
    }

//    public static void main(String[] args) {
//        crawl();
//    }
}
