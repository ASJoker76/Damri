package com.app.template.connection;

public class Host {
    private final static String host = "https://testapi.damri.co.id/index.php/";
    private final static String token = "463d2c83201694a5404d1e2d58b0350c";
    public static String getHost() {
        return host;
    }
    public static String getToken(){return token;}
}
