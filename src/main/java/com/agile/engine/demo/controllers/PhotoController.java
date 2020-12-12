package com.agile.engine.demo.controllers;

import com.agile.engine.demo.domain.Photo;
import com.agile.engine.demo.domain.dto.PhotoResponseDto;
import com.agile.engine.demo.security.AuthenticationService;
import com.agile.engine.demo.service.FetchDataFromExternalApiService;
import com.agile.engine.demo.service.PhotoService;
import com.agile.engine.demo.service.mappers.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/photos")
public class PhotoController {
    private final FetchDataFromExternalApiService fetchDataFromExternalApiService;
    private final AuthenticationService authenticationService;
    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @Autowired
    public PhotoController(FetchDataFromExternalApiService fetchService,
                           AuthenticationService authenticationService,
                           PhotoService photoService, PhotoMapper photoMapper) {
        this.authenticationService = authenticationService;
        this.fetchDataFromExternalApiService = fetchService;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
    }

    @GetMapping
    public List<PhotoResponseDto> getPhotos() throws Exception {
        return photoService.getAll().stream()
                .map(photoMapper::mapPhotoToResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public PhotoResponseDto fetchById(@PathVariable String id) throws IOException {
        return photoMapper.mapPhotoToResponseDto(photoService.getById(id));
    }

    @GetMapping("/search")
    public List<PhotoResponseDto> search(@RequestParam MultiValueMap<String, String> params) {
        return photoService.search(params).stream()
                .map(photoMapper::mapPhotoToResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/pages")
    public Page<Photo> getPage(@RequestParam int page) {
        return photoService.getPage(page);
    }
}
