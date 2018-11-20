package com.td.util;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

import java.util.HashMap;

public class BaiduAPI {
    public static final String APP_ID = "11832082";
    public static final String API_KEY = "VXZshIW17GmFvNoX7BLReGgC";
    public static final String SECRET_KEY = "pGg2p2xE6XQkjGdEdSlXibyEPolTNpCa";
    
    public static JSONObject baiduAPI(String text1, String text2) {
    	AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("model", "CNN");

        JSONObject res = client.simnet(text1, text2, options);
        
        return res;
    }
    
    public static JSONObject fenci(String text) {
    	
    	AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
        HashMap<String, Object> options = new HashMap<String, Object>();
        JSONObject res = client.lexer(text, options);
        
        return res;

    }

}
