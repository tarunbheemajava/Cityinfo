package com.city.cityinfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

	private Map<String, String> codes;

	private Map<String, String> names;

	public CityController() {
		names = new HashMap<String, String>();
		names.put("australia", "Canberra");
		names.put("canada", "Ottawa");
		names.put("england", "London");
		names.put("united states", "Washington");

		codes = new HashMap<String, String>();
		codes.put("au", "Canberra");
		codes.put("ca", "Ottawa");
		codes.put("uk", "London");
		codes.put("usa", "Washington");

	}

	@GetMapping("/city")
	@ResponseBody
	public City getCity(@RequestParam(name = "name", required = true) String name) {
		String cityvalue = null;
		if (name != null && !name.trim().equals("")) {
			cityvalue = codes.get(name.toLowerCase());
			if (cityvalue == null) {
				cityvalue = names.get(name.toLowerCase());
			}
		}
		if (cityvalue == null) {
			throw new ResourceNotFoundException("Not Found");
		}
		City city = new City();
		city.setCapitalCity(cityvalue);
		return city;
	}

}
