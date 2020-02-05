package cn.edu.nju.user.util;

import cn.edu.nju.user.dao.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    public UserGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/generate")
    public boolean generateUserRest() {
        try {
            generateUser();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void generateUser() throws IOException {
        //1000 - 插入1000条数据6min，也太慢了！！！！！！
        String username;
        for (int i = 0;i < 1000;i++) {
            username = getRandomString(8);
            while (userRepository.findByUsername(username) != null) {
                username = getRandomString(8);
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
            //addresses
            user.setAddresses(getRandomAddresses(3));
            userRepository.save(user);
        }

    }

    private static String getRandomString(int length){
        String randomString = UUID.randomUUID().toString();
        return randomString.substring(0,length);
    }

    private Set<String> getRandomAddresses(int num) throws IOException {
        File file = new ClassPathResource("chinaRegions" +
                File.separator + "province.json").getFile();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(reader);
            List<JsonNode> provinces = new ArrayList<>();
            for (JsonNode provinceNode : rootNode) {
                provinces.add(provinceNode);
            }
            Set<String> addresses = new HashSet<>();
            for (int i = 0;i < num;i++) {
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
            return addresses;
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
