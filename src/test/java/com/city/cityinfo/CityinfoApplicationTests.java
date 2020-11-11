package com.city.cityinfo;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CityinfoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityinfoApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testCity() throws JSONException, JsonMappingException, JsonProcessingException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/city?name=usa"), HttpMethod.GET,
				entity, String.class);
		ObjectMapper m = new ObjectMapper();
		City city = m.readValue(response.getBody(), City.class);
		assertEquals("Washington", city.getCapitalCity());
	}

	@Test
	public void testNotFound() throws JSONException, JsonMappingException, JsonProcessingException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/city?name=ttd"), HttpMethod.GET,
				entity, String.class);
		int responseCode = response.getStatusCodeValue();
		assertEquals(404, responseCode);
	}

	@Test
	public void testCityNullValue() throws JSONException, JsonMappingException, JsonProcessingException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/city?name=null"), HttpMethod.GET,
				entity, String.class);
		int responseCode = response.getStatusCodeValue();
		assertEquals(404, responseCode);
	}

	@Test
	public void testCitySpecialCharacters() throws JSONException, JsonMappingException, JsonProcessingException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/city?name=@#$%^&"), HttpMethod.GET,
				entity, String.class);
		int responseCode = response.getStatusCodeValue();
		assertEquals(404, responseCode);
	}
	
	@Test
	public void testCityWrongUrl() throws JSONException, JsonMappingException, JsonProcessingException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/city"), HttpMethod.GET,
				entity, String.class);
		int responseCode = response.getStatusCodeValue();
		assertEquals(400, responseCode);
	}


	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
