package com.iban;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.iban.util.IBANValidateUtil;

@SpringBootApplication
public class IbanValidatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(IbanValidatorApplication.class, args);
		//validator();
	}
	
	@PostConstruct
    public static void countryIbanLength() {
    	String text ="";
    	try {
			File resource = new ClassPathResource("countryibanlength.json").getFile();
			text = new String(Files.readAllBytes(resource.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//System.out.println(text);
		IBANValidateUtil.codeLengthJson= new JSONObject(text);
	}
    

	/*
	 * public static Boolean validator() {
	 * System.out.println(IBANValidateUtil.isValid("GB02BARC20201530093451"));
	 * return true; }
	 */

}
