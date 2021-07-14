package com.iban.util;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


public  class  IBANValidateUtil {
	private static final Logger LOGGER = LogManager.getLogger(IBANValidateUtil.class);
    private static final int MAX_ALPHANUMERIC_VALUE = 35; // Z last digit is 35
    private static final long MAX = 999999999; //max number for modulus 97
    private static final long MODULUS = 97;
    private static final int MIN_LENGTH_CODE = 4;
    private static final int MAX_LENGTH_CODE = 34;
    
    public static JSONObject codeLengthJson;
    public static List<Map<String,String>> supportedCountries=new ArrayList<>();
    
    /**
     * **********************************************************************************************************************
     *  Function: To check Valid Iban                                                 
     *	@param code
     *	@return -3,-2,-1,0,1 and other integer value, 1  if iban is valid and other for non valid
     *  Author: Rinku Soni 5:37:28 pm  11-Jul-2021															       																
     ************************************************************************************************************************
     */
   public static int isValid(String code) {
    	//json not loaded
	   if(codeLengthJson==null) return -10;
	 //remove white space
       code=code.replaceAll("\\s+","");
	   if (code.length() == 0 ) return 0;
       if( code.length() < MIN_LENGTH_CODE ) return -4;
       if( code.length() > MAX_LENGTH_CODE) return -5;
    	
        
        //get first two country code
        String countryCode=code.substring(0,2).toUpperCase();
       //if first two char is not A-Z
        if(!countryCode.matches("^[A-Z]{2}")) { return -11;}//invalid
        /*check if the key is available or not 
        wiki :1 Check that the total IBAN length is correct as per the country. 
        If not, the IBAN is invalid*/
        if( !codeLengthJson.has(countryCode)) {
        	return -1; //country doesn't exist
        }
        if(codeLengthJson.getInt(countryCode)!=code.length()) {
        	return -2; //length don't match with given codes
        }

        /*
         * move first 4 digit in last
         * */
        String reFormattedCode = code.substring(4) + code.substring(0, 4);
        //calculate modulus
        return calculateModulus(reFormattedCode); //1 for true
    }
    /**
     * **********************************************************************************************************************
     *  Function: Modulus of reformatted code of ASCII CHARACTER with max 10 digit                                            
     *	@param reFormattedCode
     *	@return
     *  Author: Rinku Soni (5424) 5:52:54 pm  11-Jul-2021															       																
     ************************************************************************************************************************
     */
    private static int calculateModulus(String reFormattedCode)  {
    	
        long total = 0;
        
        for (int i = 0; i < reFormattedCode.length(); i++) {
        	//A-Z=10-35 ASCII VALUES
        	int charValue = Character.getNumericValue(reFormattedCode.charAt(i));
            //char value should be 0 to 35 
            if (charValue < 0 || charValue > MAX_ALPHANUMERIC_VALUE) { //if 
               return -3;// weird character
            }
            // add characters (~ 1,2) in last
            total = (charValue > 9 ? total * 100 : total * 10) + charValue;
            //loop till 10 digits then do modulus
            if (total > MAX) {
                total = total % MODULUS;
            }
        }
        return (int)(total % MODULUS);
    }
    
    public static String isValidCodeMessageDesc(int code) {
    	String message="Invalid";
    	switch (code) {
			case 1: message="Valid";
				break;
			case 0:message="String is EMPTY";
				break;
			case -1:message="We don't Support this country right now";
				break;
			case -2:message="Number of character is not Correct with respect to country";
				break;
			case -3:message="Found weird character in Between";
				break;
			case -4:message="IBAN should be Minimum "+MIN_LENGTH_CODE+ " Character Long";
			break;
			case -5:message="IBAN should not be Bigger than "+MAX_LENGTH_CODE+ " Characters";
			break;
			case -10 :message="JSON file which has country length Not found";
			break;
			default:
				message="Invalid";
				break;
		}
    	return message;
    }
   
}