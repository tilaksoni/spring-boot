package com.iban.util;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


public  class  IBANValidateUtil {
	private static final Logger LOGGER = LogManager.getLogger(IBANValidateUtil.class);
    private static final int MAX_ALPHANUMERIC_VALUE = 35; // Z last digit is 35
    private static final long MAX = 999999999; //max number for modulus 97
    private static final long MODULUS = 97;
    
    public static JSONObject codeLengthJson;
    
    /**
     * **********************************************************************************************************************
     *  Function: To check Valid Iban                                                 
     *	@param code
     *	@return -3,-2,-1,0,1 and other integer value, 1  if iban is valid and other for non valid
     *  Author: Rinku Soni 5:37:28 pm  11-Jul-2021															       																
     ************************************************************************************************************************
     */
   public static int isValid(String code) {
    	
	   //basic null check
    	if (code == null ) return 0;
    	
    	//remove white space
        code=code.replaceAll("\\s+","");
        
        //get first two country code
        String countryCode=code.substring(0,2).toUpperCase();

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
			case 0:message="String is Empty";
				break;
			case -1:message="We don't Support this country right now";
				break;
			case -2:message="Number of character is not Correct with respect to country";
				break;
			case -3:message="Found weird character in Between";
				break;
			case -10 :message="JSON with country length Not found";
			break;
			default:
				message="Invalid";
				break;
		}
    	return message;
    }
   
}