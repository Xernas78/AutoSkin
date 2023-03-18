package dev.xernas.autoskinplugin.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpUtils {

    public static JSONObject getRequest(String targetURL) {
        try {


            URL url = new URL(targetURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(informationString.toString());

                return json;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
