package Air.FourCore.network;

import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;


public class GetRequest {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static String uuidToName(UUID uuid) throws ParseException {
        String result;
        try {
            result = sendGet("https://api.mojang.com/user/profiles/" + uuid.toString() + "/names");
            //System.out.println(result);
        }catch(Exception e) {
            result = null;
        }
        int last = result.indexOf("\"},");
        int first = result.indexOf(":") + 2;
        result = result.substring(first, last);
        //System.out.println(result);
        return result;
    }

    public static String sendGet(String targetUrl) throws Exception {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET"); // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); // add request header
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //System.out.println("HTTP 응답 코드 : " + responseCode);
        //System.out.println("HTTP body : " + response.toString());
        return response.toString();
    }
}