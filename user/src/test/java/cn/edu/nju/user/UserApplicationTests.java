package cn.edu.nju.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.user.User;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UserApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testBCrypt() {
		String pwd1 = "12345621312324askdljaslkdjoiqursadjkadmzxcnmzxmcnzx,mcn,xzmnczxklkasdlkasjdlkasjdlaksjd";
		String pwd2 = "password";
		String hashed = BCrypt.hashpw(pwd1, BCrypt.gensalt());
		System.out.println(hashed.length());
		System.out.println(BCrypt.checkpw(pwd1, hashed));
	}

	@Test
	public void testObjectMapper() {
		User user = new User();
		user.setId(1091L);
		Set<String> addresses = new HashSet<>();
		addresses.add("beijing");
		addresses.add("上海");
		user.setAddresses(addresses);

		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(user));
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testUniqueString() {
		String str = UUID.randomUUID().toString();
		System.out.println("UUID's length: " + str.length());
		System.out.println(str.substring(0,8));
	}

	/**
	 * https://www.baeldung.com/spring-classpath-file-access#2-reading-as-aninputstream
	 */
	@Test
	public void testGenerateAddress() throws IOException {
		File file = new ClassPathResource("chinaRegions" +
				File.separator + "province.json").getFile();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(reader);
			List<JsonNode> provinces = new ArrayList<>();
			for (JsonNode provinceNode : rootNode) {
				provinces.add(provinceNode);
			}
			int randomProvince = new Random().nextInt(provinces.size());
			JsonNode province = provinces.get(randomProvince);
			System.out.println("name: " + province.get("name") + " id: " + province.get("id"));
		}
	}

	@Test
	public void testGetCity() throws IOException {
		File cityFile = new ClassPathResource("chinaRegions/city.json").getFile();
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream(cityFile), StandardCharsets.UTF_8)) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(reader);
			JsonNode node = rootNode.get("130000000000");
			List<JsonNode> cities = new ArrayList<>();
			for (JsonNode cityNode : node) {
				cities.add(cityNode);
			}
			int randomCity = new Random().nextInt(cities.size());
			JsonNode city = cities.get(randomCity);
			System.out.println("name: " + city.get("name") + " id: " + city.get("id"));
		}
	}

	@Test
	public void testGetCountry() throws IOException {
		File countyFile = new ClassPathResource("chinaRegions/county.json").getFile();
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream(countyFile), StandardCharsets.UTF_8)) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(reader);
			JsonNode node = rootNode.get("131100000000");
			List<JsonNode> counties = new ArrayList<>();
			for (JsonNode countiesNode : node) {
				counties.add(countiesNode);
			}
			int randomCounty = new Random().nextInt(counties.size());
			JsonNode county = counties.get(randomCounty);
			System.out.println("name: " + county.get("name") + " id: " + county.get("id"));
		}
	}

	@Test
	public void testGetTown() throws IOException{
		File townFile = new ClassPathResource("chinaRegions/town.json").getFile();
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream(townFile), StandardCharsets.UTF_8)) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(reader);
			JsonNode node = rootNode.get("131182000000");
			List<JsonNode> counties = new ArrayList<>();
			for (JsonNode countiesNode : node) {
				counties.add(countiesNode);
			}
			int randomCounty = new Random().nextInt(counties.size());
			JsonNode county = counties.get(randomCounty);
			System.out.println("name: " + county.get("name") + " id: " + county.get("id"));
		}
	}

	@Test
	public void getRandomAddresses() throws IOException {
		File file = new ClassPathResource("chinaRegions" +
				File.separator + "province.json").getFile();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(reader);
			List<JsonNode> provinces = new ArrayList<>();
			for (JsonNode provinceNode : rootNode) {
				provinces.add(provinceNode);
			}
			int randomProvince = new Random().nextInt(provinces.size());
			JsonNode province = provinces.get(randomProvince);
//            System.out.println("name: " + province.get("name") + " id: " + province.get("id"));
			JsonNode city = getInfo("city", province.get("id").asText());
			JsonNode county = getInfo("county", city.get("id").asText());
			JsonNode town = getInfo("town", county.get("id").asText());
			System.out.println(province.get("name").asText() + "-"
					+ city.get("name").asText() + "-"
					+ county.get("name").asText() + "-"
					+ town.get("name").asText());
		}
	}

	private JsonNode getInfo(String level, String id) throws IOException {
		File file = new ClassPathResource("chinaRegions/" + level + ".json").getFile();
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(reader);
			JsonNode locationNode = rootNode.get(id);
			List<JsonNode> locations = new ArrayList<>();
			for (JsonNode node : locationNode) {
				locations.add(node);
			}
			int randomCity = new Random().nextInt(locations.size());
			return locations.get(randomCity);
		}
	}
}
