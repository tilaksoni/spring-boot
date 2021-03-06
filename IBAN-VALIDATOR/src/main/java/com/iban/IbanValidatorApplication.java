package com.iban;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.iban.util.IBANValidateUtil;

@SpringBootApplication
public class IbanValidatorApplication {
	private static final Logger LOGGER = LogManager.getLogger(IbanValidatorApplication.class);
	private static List<Map<String,String>> supportedCountries=new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(IbanValidatorApplication.class, args);
		//validator();
	}
	
	@PostConstruct
    public static void countryIbanLength() {
    	String countryibanlengthString ="",countriesString="";
    	
    	try {
			
    		//File resource = new ClassPathResource("countryibanlength.json").getFile();
    		ClassPathResource resource = new ClassPathResource("countryibanlength.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            countryibanlengthString = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
    		//countryibanlengthString = new String(Files.readAllBytes(resource.toPath()));
            //File countryFile = new ClassPathResource("countries.json").getFile();
             ClassPathResource resource2 = new ClassPathResource("countries.json");
			 BufferedReader reader2 = new BufferedReader(new InputStreamReader(resource2.getInputStream()));
    		 countriesString = reader2.lines().collect(Collectors.joining("\n"));
             reader2.close();
             System.out.println(countryibanlengthString);
			//countriesString = new String(Files.readAllBytes(countryFile.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LOGGER.error("Error Message {}",e.getMessage());
		}
		//System.out.println(text);
    	JSONObject codeLengthJson=new JSONObject(countryibanlengthString);
    	JSONObject countries=new JSONObject(countriesString);
    	
		IBANValidateUtil.codeLengthJson= codeLengthJson;
		codeLengthJson.keySet().forEach(keyStr ->
	    {
	    	LOGGER.info("IbanValidatorApplication.countries {}",countries.get(keyStr));
	    	Map<String,String> supportedCountry=new HashMap<>();
	    	supportedCountry.put("code", keyStr);
	    	supportedCountry.put("country", countries.getString(keyStr));
	    	supportedCountries.add(supportedCountry);
	     });
		IBANValidateUtil.supportedCountries=supportedCountries;
	}
	
	

	/*
	 * public static Boolean validator() {
	 * System.out.println(IBANValidateUtil.isValid("GB02BARC20201530093451"));
	 * return true; }
	 */

}
