package com.iban.util;

import org.json.JSONObject;


public  class  IBANValidateUtil {
	private static final int MIN_CODE_LEN = 5;
    private static final int MAX_ALPHANUMERIC_VALUE = 35; // Z last digit is 35
    private static final long MAX = 999999999;
    private static final long MODULUS = 97;
    private static final String CODE_LENGTHS = "{\"AL\":28,\"AD\":24,\"AT\":20,\"AZ\":28,\"BH\":22,\"BE\":16,\"BA\":20,\"BR\":29,\"BG\":22,\"CR\":21,\"HR\":21,\"CY\":28,\"CZ\":24,\"DK\":18,\"DO\":28,\"EE\":20,\"FO\":18,\"FI\":18,\"FR\":27,\"GE\":22,\"DE\":22,\"GI\":23,\"GR\":27,\"GL\":18,\"GT\":28,\"HU\":28,\"IS\":26,\"IE\":22,\"IL\":23,\"IT\":27,\"JO\":30,\"KZ\":20,\"KW\":30,\"LV\":21,\"LB\":28,\"LI\":21,\"LT\":20,\"LU\":20,\"MK\":19,\"MT\":31,\"MR\":27,\"MU\":30,\"MC\":27,\"MD\":24,\"ME\":22,\"NL\":18,\"NO\":15,\"PK\":24,\"PS\":29,\"PL\":28,\"PT\":25,\"QA\":29,\"RO\":24,\"SM\":27,\"SA\":24,\"RS\":22,\"SK\":24,\"SI\":19,\"ES\":24,\"SE\":24,\"CH\":21,\"TN\":24,\"TR\":26,\"AE\":23,\"GB\":22,\"VG\":24}";
    /**
     * **********************************************************************************************************************
     *  Function: To check Valid Iban                                                 
     *	@param code
     *	@return true, if iban is valid and false for non valid
     *  Author: Rinku Soni 5:37:28 pm  11-Jul-2021															       																
     ************************************************************************************************************************
     */
   public static boolean isValid(String code) {
    	//basic null && minimum code length check
    	if (code == null ) return false;
        code=code.replaceAll("\\s+","");//remove white space
        if(code.length()<MIN_CODE_LEN) return false;

        //conversion of code length string to JSON object
        JSONObject codeLengthJson=new JSONObject(CODE_LENGTHS);

        //get first two country code
        String countryCode=code.substring(0,2).toUpperCase();

        /*check if the key is available or not 
        wiki :1 Check that the total IBAN length is correct as per the country. 
        If not, the IBAN is invalid*/
        if( ! (codeLengthJson.has(countryCode) && codeLengthJson.getInt(countryCode)==code.length())) {
        	return false;
        }

        /*
         * Check that the total IBAN length is correct as per the country. 
         * If not, the IBAN is invalid
         * */
        String reFormattedCode = code.substring(4) + code.substring(0, 4);
        //calculate modulus
        return calculateModulus(reFormattedCode)==1;
    }
    /**
     * **********************************************************************************************************************
     *  Function: Construct N from the first 9 digits of D
		N = 321428291
		Calculate N mod 97 = 70
		Construct a new 9-digit N from the above result (step 2) followed by the next 7 digits of D.
		N = 702345698
		Calculate N mod 97 = 29
		Construct a new 9-digit N from the above result (step 4) followed by the next 7 digits of D.
		N = 297654321
		Calculate N mod 97 = 24
		Construct a new N from the above result (step 6) followed by the remaining 5 digits of D.
		N = 2461182
		Calculate N mod 97 = 1                                                  
     *	@param reFormattedCode
     *	@return
     *  Author: Rinku Soni (5424) 5:52:54 pm  11-Jul-2021															       																
     ************************************************************************************************************************
     */
    private static int calculateModulus(String reFormattedCode)  {
        long total = 0;
        for (int i = 0; i < reFormattedCode.length(); i++) {
            int charValue = Character.getNumericValue(reFormattedCode.charAt(i));
            if (charValue < 0 || charValue > MAX_ALPHANUMERIC_VALUE) {
               return 0;
            }
            total = (charValue > 9 ? total * 100 : total * 10) + charValue; 
            if (total > MAX) {
                total = total % MODULUS;
            }
        }
        return (int)(total % MODULUS);
    }
}