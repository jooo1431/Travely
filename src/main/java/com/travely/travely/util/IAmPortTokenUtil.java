package com.travely.travely.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class IAmPortTokenUtil {

    @Value("${iamportKey}")
    private static String imp_key;

    @Value("${iamportSecret}")
    private static String imp_secret;

    public static String createToken() {

        String token = "nothing";

        try {
            //HTTP 접속
            URL url = new URL("https://api.iamport.kr/users/getToken");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //전달방식은 POST방식으로 한다.
            connection.setRequestMethod("POST");
            //서버로 데이터를 전송할수 있도록하자.
            connection.setDoOutput(true);
            //서버로부터 데이터를 받을수 있도록하자.
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            //헤더값 설정
            connection.setRequestProperty("Content-Type", "application/json");

            //보내줄 데이터를 설정
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("imp_key", imp_key);
            jsonObject.addProperty("imp_secret", imp_secret);
            final String json = gson.toJson(jsonObject);

            //데이터 전송
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(json);
            wr.flush();

            //response로 받자
            final int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(connection.getInputStream(), "utf-8");
                BufferedReader br = new BufferedReader(in);

                String line;
                String result = "";

                while ((line = br.readLine()) != null) {
                    result = result + line + "\n";
                }
                br.close();

                //토큰 값만 추려내자.
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);
                token = element.getAsJsonObject().get("response").getAsJsonObject().get("access_token").getAsString();

            } else {
                log.info("Http Status Code : " + HttpResult);
            }

        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return token;
    }
}
