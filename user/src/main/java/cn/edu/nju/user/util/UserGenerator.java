package cn.edu.nju.user.util;

import cn.edu.nju.user.dao.BatchDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.user.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
public class UserGenerator {
    private BatchDao<User> batchDao;

    @Autowired
    public UserGenerator(BatchDao<User> userRepositoryImpl) {
        this.batchDao = userRepositoryImpl;
    }

    @GetMapping("/user/generate")
    public boolean generateUserRest() {
        try {
            generateUsers(100);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void generateUsers(int userNum) throws IOException {
        List<User> list = new ArrayList<>();
        String username;
        List<Set<String>> addresses = getRandomAddresses(3, userNum);
        for (int i = 0;i < userNum;i++) {
            username = getRandomString(8);
            User user = new User();
            user.setUsername(username);
            user.setMobile("1801900" + String.format("%04d", i));
            user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
            //addresses
            user.setAddresses(addresses.get(i));
            list.add(user);
        }
        batchDao.batchSave(list);
    }


    public static String getRandomString(int length){
        String randomString = UUID.randomUUID().toString();
        return randomString.substring(0,length);
    }

    private List<Set<String>> getRandomAddresses(int addressNum, int userNum) throws IOException {
        File file = new ClassPathResource("chinaRegions" +
                File.separator + "province.json").getFile();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(reader);
            List<JsonNode> provinces = new ArrayList<>();
            for (JsonNode provinceNode : rootNode) {
                provinces.add(provinceNode);
            }
            List<Set<String>> results = new ArrayList<>();
            for (int k = 0;k < userNum;k++) {
                Set<String> addresses = new HashSet<>();
                for (int i = 0;i < addressNum;i++) {
                    int randomProvince = new Random().nextInt(provinces.size());
                    JsonNode province = provinces.get(randomProvince);
                    JsonNode city = getInfo("city", province.get("id").asText());
                    if (city == null) continue;
                    JsonNode county = getInfo("county", city.get("id").asText());
                    if (county == null) continue;
                    JsonNode town = getInfo("town", county.get("id").asText());
                    StringBuilder builder = new StringBuilder();
                    builder.append(province.get("name").asText()).append("-");
                    builder.append(city.get("name").asText()).append("-");
                    builder.append(county.get("name").asText());
                    if (town != null) {
                        builder.append("-").append(town.get("name").asText());
                    }
                    addresses.add(builder.toString());
                }
                results.add(addresses);
            }
            return results;
        }
    }

    private JsonNode getInfo(String level, String id) throws IOException {
        File file = new ClassPathResource("chinaRegions/" + level + ".json").getFile();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(reader);
            JsonNode locationNode = rootNode.get(id);
            if (locationNode == null) {
                return null;
            }
            List<JsonNode> locations = new ArrayList<>();
            for (JsonNode node : locationNode) {
                locations.add(node);
            }
            int randomCity = new Random().nextInt(locations.size());
            return locations.get(randomCity);
        }
    }
}
