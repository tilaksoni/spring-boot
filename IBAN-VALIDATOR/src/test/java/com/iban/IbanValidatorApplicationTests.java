package com.iban;

import static org.junit.Assert.assertEquals;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.iban.controller.restcontroller.MainRestController;
import com.iban.service.MainService;

@SpringBootTest

class IbanValidatorApplicationTests {

	@Autowired
	private MainService mainService;
	private static Boolean VALID=true;
	private static Boolean INVALID=false;
	
	@Test
	public void singleIBANTest() {
		String first="AD1319798039630122776418";
		JSONArray response=mainService.validateIBANList(first);
		assertEquals("Simple length test",response.length(),1);
		assertEquals( "simple test case "+first, response.getJSONObject(0).get("validFlag"), VALID);
		
		String second="BH08XIEF12815287328115";
		assertEquals( "simple test case "+second, mainService.validateIBANList(second).getJSONObject(0).getBoolean("validFlag"), VALID);
		
		String third="CZ4193300548250950298955";
		assertEquals( "simple test case "+third, mainService.validateIBANList(third).getJSONObject(0).getBoolean("validFlag"), VALID);
		
		
		String fourth="BR9763141177149119425139874U1";
		assertEquals( "simple test case "+fourth, mainService.validateIBANList(fourth).getJSONObject(0).getBoolean("validFlag"), VALID);
		
		
		String fifth="AD13  1979 803963 0122776418";
		assertEquals( "simple test case "+fifth, mainService.validateIBANList(fifth).getJSONObject(0).getBoolean("validFlag"), VALID);
		
		
		String sixth="CZ41933005482509502989553";
		assertEquals( "simple test case "+sixth, mainService.validateIBANList(sixth).getJSONObject(0).getBoolean("validFlag"), INVALID);
		
		String seventh="";
		assertEquals( "simple test case "+seventh, mainService.validateIBANList(seventh).length(), 0);
		
		String eight=",,,   ,,,,  ,,,";
		assertEquals( "simple test case "+seventh, mainService.validateIBANList(eight).length(), 3);
		
		String nine="CZ41933005#82509502989553";
		assertEquals( "simple test case "+nine, mainService.validateIBANList(nine).getJSONObject(0).getBoolean("validFlag"), INVALID);
		
	}
	@Test
	public void listIBAN() {
		JSONArray response=mainService.validateIBANList("EE225526502575365444,AD1319798039637122776418,TEST");
		assertEquals( "Regular multiplication should work", response.length(), 3);
		JSONArray response2=mainService.validateIBANList("EE225526502575365444,AD1319798  039637122776418,TEST"
				+ ",VA85495026167665328674\n" + 
				"VG96XJMN3445490977391640");
		assertEquals( "Regular multiplication should work", response2.length(), 5);
	}

}
