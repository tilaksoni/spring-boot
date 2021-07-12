package com.iban.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.iban.service.MainService;
import com.iban.util.IBANValidateUtil;

@Service
public class MainServiceImpl implements MainService {
	private static final Logger LOGGER = LogManager.getLogger(MainService.class);
	@Override
	public JSONArray validateIBANList(String codes) {
		//String[] codesArray=codes.split("[\\n,@&.?$+-]+"); //UNCOMMENT TO ADD MORE SPECIAL CHARACTER
		String[] codesArray=codes.split("[\\n,]+");
		JSONArray resultArray=new JSONArray();
		for(String code:codesArray) {
			LOGGER.info("code: {}",code);
			resultArray.put(new JSONObject().put(code, IBANValidateUtil.isValid(code)));
		}
		return resultArray;
	}
	
	@Override
	public JSONArray validateIBANFile(MultipartFile file) {
		JSONArray resultArray=new JSONArray();
		if (!file.isEmpty()) {
	        try {
	            byte[] bytes = file.getBytes();
	            String completeData = new String(bytes);
	            System.out.println(completeData);
	            String[] codesArray=completeData.split("[\\n]+");
	    		for(String code:codesArray) {
	    			System.out.println(code);
	    			resultArray.put(new JSONObject().put(code, IBANValidateUtil.isValid(code)));
	    		}
	    		}catch (Exception e) {
	    			LOGGER.error("Exception: {}"+e.getMessage());
	    			return resultArray;

	    		}
		}
		return resultArray;
	}



}
 