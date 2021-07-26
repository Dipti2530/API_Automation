package util;

public class RestEndpoints {

    private static final String SERVICE_URL = "https://api.wheretheiss.at";

    public static String getUrlWithPath(String path) {
        if (path.startsWith("/"))
            return SERVICE_URL + path;
        else {
            return SERVICE_URL + "/" + path;
        }
    }
}
