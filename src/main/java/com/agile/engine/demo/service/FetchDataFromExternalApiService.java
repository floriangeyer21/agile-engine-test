package com.agile.engine.demo.service;

import com.agile.engine.demo.domain.Photo;
import com.agile.engine.demo.domain.dto.PhotoRequestDto;
import com.agile.engine.demo.domain.Picture;
import com.agile.engine.demo.domain.Response;
import com.agile.engine.demo.exceptions.DataProcessingException;
import com.agile.engine.demo.security.AuthenticationService;
import com.agile.engine.demo.service.mappers.PhotoMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class FetchDataFromExternalApiService {
    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final AuthenticationService authenticationService;
    @Value("${external.api.url}")
    private String externalApiUrl;
    private int currentPage = 1;

    public FetchDataFromExternalApiService(PhotoService photoService,
                                           PhotoMapper photoMapper, AuthenticationService authenticationService) {
        this.photoService = photoService;
        this.photoMapper = photoMapper;
        this.authenticationService = authenticationService;
    }

    public Response fetch() {
        try {
            String token = authenticationService.getToken();
            URL url = new URL(externalApiUrl + "?pages=" + currentPage);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            Response resp = new Gson().fromJson(response.toString(), Response.class);
            for (Picture picture : resp.getPictures()) {
                fetchById(picture.getId());
            }
            if (currentPage <= resp.getPageCount() && resp.isHasMore()) {
                currentPage++;
            } else {
                currentPage = 1;
            }
            return resp;
        } catch (DataProcessingException | IOException e) {
            throw new DataProcessingException("Can't fetch date from page " + currentPage, e);
        }
    }

    private void fetchById(String id) {
        try {
            String token = authenticationService.getToken();
            URL url = new URL(externalApiUrl + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            PhotoRequestDto photoRequestDto = new Gson().fromJson(response.toString(), PhotoRequestDto.class);
            Photo photo = photoService.save(photoMapper.mapInputDtoToPhoto(photoRequestDto));
        } catch (DataProcessingException | IOException e) {
            throw new DataProcessingException("Can't fetch image with id " + id, e);
        }
    }
}
