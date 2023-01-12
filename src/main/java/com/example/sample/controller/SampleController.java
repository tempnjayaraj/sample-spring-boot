package com.example.sample.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String getString() {
		return "Greetings from Spring Boot";
	}
	
	@RequestMapping(value = "/falseEndPoint", method = RequestMethod.GET)
	@ResponseBody
	public Boolean returnFalse() {
		return false;
	}
	
	@RequestMapping(value = "/trueEndPoint", method = RequestMethod.GET)
	@ResponseBody
	public Boolean returnTrue() {
		return true;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@ResponseBody
	public Boolean validateUser(@RequestBody JSONObject user)
			throws UnsupportedEncodingException, IOException, ParseException {
		String username = (String) user.get("username");
		String password = (String) user.get("password");
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("users.json");
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(is, "UTF-8"));
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			String testuname = (String) obj.get("username");
			String testpass = (String) obj.get("password");
			if(testuname.equals(username)&&testpass.equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	@ResponseBody
	public double bodmas(@RequestBody String user)
			throws UnsupportedEncodingException, IOException, ParseException {
		return Calculator.evaluate(user);
	}
}
