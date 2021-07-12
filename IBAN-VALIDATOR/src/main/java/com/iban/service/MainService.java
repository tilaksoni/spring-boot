package com.iban.service;

import org.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;

public interface MainService {
	/**
	 * **********************************************************************************************************************
	 *  Function: To Validate Single IBAN or List distinguish 
	 *            by either special character comma(,) or /n enter new line character                                                
	 *	@param codes
	 *	@return, json array with valid iban list
	 *  Author: Rinku Soni (5424) 4:12:40 pm  11-Jul-2021															       																
	 ************************************************************************************************************************
	 */
	public JSONArray validateIBANList(String codes);
	/**
	 * **********************************************************************************************************************
	 *  Function: To Validate csv,text file, the code should be distinguish by new line character                                                
	 *	@param file
	 *	@return, json array with valid iban list
	 *  Author: Rinku Soni (5424) 3:22:30 pm  12-Jul-2021															       																
	 ************************************************************************************************************************
	 */
	public JSONArray validateIBANFile(MultipartFile file);


}