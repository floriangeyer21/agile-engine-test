package com.agile.engine.demo.security;

import com.agile.engine.demo.domain.Bearer;
import com.agile.engine.demo.exceptions.DataProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.PostConstruct;

@Service
public class AuthenticationService {
    @Value("${external.api.url.auth}")
    private String externalAuthUrl;
    @Value("${apikey}")
    private String apikey;
    private String token;

    @PostConstruct
    public void initialize() {
        getBearerToken();
    }

    public void getBearerToken() {
        try {
            URL url = new URL(externalAuthUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String json = " { \"apiKey\" : \"" + apikey + "\" } ";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            Bearer bearer = new Gson().fromJson(response.toString(), Bearer.class);
            if (bearer.isAuth()) {
                this.token = bearer.getToken();
            } else {
                getBearerToken();
            }
        } catch (DataProcessingException | IOException e) {
            throw new DataProcessingException("Can't retrieve bearer token ", e);
        }
    }

    public String getToken() {
        return this.token;
    }
}
