package com.iban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IbanValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbanValidatorApplication.class, args);
		//validator();
	}

	/*
	 * public static Boolean validator() {
	 * System.out.println(IBANValidateUtil.isValid("GB02BARC20201530093451"));
	 * return true; }
	 */

}
