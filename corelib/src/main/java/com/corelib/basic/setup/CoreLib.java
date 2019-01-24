package com.corelib.basic.setup;


/**
 * @author Created by Abhijit on 13-Dec-16.
 *
 * CoreLib.newInstance()
 *                 .setTestVersion("0.1")
 *                 .setBaseUrl("http://dennislab.com");
 */
public class CoreLib {

    private static CoreLib _Instance;
    private String BASE_URL;

    private static String SHARE_URL = "http://bdoindia.app.link/category?";//https://www.appsflyer.com
    public static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";


    public static CoreLib newInstance() {
        if (_Instance == null) {
            _Instance = new CoreLib();
        }

        return _Instance;
    }

    public static CoreLib getInstance() {
        return _Instance;
    }


    public String getBaseUrl() {
        return BASE_URL;
    }

    public String getTermsOfUseUrl() {
        return BASE_URL + "/index.php/files/terms_of_use.pdf";
    }

    public String getPrivacyPolicyUrl() {
        return BASE_URL + "/index.php/files/privacy_policy.pdf";
    }

    //"http://website.com"
    public void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }
}

/* New APIs required for this App

API 1:   http://website.com/index.php/Rest/signup

Params: name,mobile,email,panNo,pass,profileType

Response: success/failure.(return success and basic info similar to login api)

*/

/*
    1 1xx Informational.
    2 2xx Success.
    3 3xx Redirection.
    4 4xx Client Error.
    5 5xx Server Error.
*/
