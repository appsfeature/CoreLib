package com.dennislabs.corelib.setup;


/**
 * Created by Abhijit on 13-Dec-16.
 *
 * CoreLib.newInstance(APPLICATION_KEYWORD)
 *                 .setTestVersion("0.1")
 *                 .setBaseUrl("http://dennislab.com");
 */
@SuppressWarnings("all")
public class CoreLib {

    private static CoreLib _Instance;
    private static String TEST_VERSION = "0.1";
    public static String APP_KEYWORD;
    public String BASE_URL;

    private static String SHARE_URL = "http://bdoindia.app.link/category?";//https://www.appsflyer.com
    public static final String PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=";


    public static CoreLib newInstance(String appKeyword) {
        if (_Instance == null) {
            _Instance = new CoreLib(appKeyword);
        }

        return _Instance;
    }

    public static CoreLib getInstance() {
        return _Instance;
    }

    private CoreLib(String appKeyword) {
        APP_KEYWORD = appKeyword;
    }


    public CoreLib setTestVersion(String testVersion) {
        CoreLib.TEST_VERSION = testVersion;
        return this;
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

/* New API's required for this App

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
