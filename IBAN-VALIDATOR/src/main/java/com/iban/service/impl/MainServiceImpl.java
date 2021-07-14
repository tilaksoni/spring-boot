package com.iban.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.iban.service.MainService;
import com.iban.util.IBANValidateUtil;

@Service
public class MainServiceImpl implements MainService {
	private static final Logger LOGGER = LogManager.getLogger(MainService.class);
	@Override
	public JSONArray validateIBANList(String codes) {
		//String[] codesArray=codes.split("[\\n,@&.?$+-]+"); //UNCOMMENT TO ADD MORE SPECIAL CHARACTER
		return validateStringData(codes);
	}
	
	@Override
	public JSONArray validateIBANFile(MultipartFile file) {
		JSONArray resultArray=new JSONArray();
		
		if (!file.isEmpty()) {
	        try {
	            byte[] bytes = file.getBytes();
	            String completeData = new String(bytes);
	            resultArray=validateStringData(completeData);
	    		}catch (Exception e) {
	    			LOGGER.error("Exception: {}",e.getMessage());
	    			return resultArray;

	    		}
		}
		return resultArray;
	}
	
	
	public JSONArray validateStringData(String data) {
		JSONArray resultArray=new JSONArray();
		if(data!=null && !data.equals("")) {
			String[] codesArray=data.split("[\\n,]+");
			for(String code:codesArray) {
				int validCode=IBANValidateUtil.isValid(code);
				resultArray.put(new JSONObject()
						.put("iban",code)
						.put("message", IBANValidateUtil.isValidCodeMessageDesc(validCode))
						.put("validFlag", validCode==1)
						.put("code", validCode)
						
						);
			}
		}
		return resultArray;
	}

	@Override
	public JSONArray getListOfSupportedCountries() {
		System.out.println(new Gson().toJson(IBANValidateUtil.supportedCountries));
		return new JSONArray(IBANValidateUtil.supportedCountries);//just for fun
	}
	
	



}
 