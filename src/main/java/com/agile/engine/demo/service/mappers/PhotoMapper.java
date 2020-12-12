package com.agile.engine.demo.service.mappers;

import com.agile.engine.demo.domain.Photo;
import com.agile.engine.demo.domain.dto.PhotoRequestDto;
import com.agile.engine.demo.domain.Tag;
import com.agile.engine.demo.domain.dto.PhotoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoMapper {
    private final TagMapper tagMapper;

    @Autowired
    public PhotoMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    public Photo mapInputDtoToPhoto(PhotoRequestDto photoRequestDto) {
        List<Tag> tags = new ArrayList<>();
        for (String str : photoRequestDto.getTags().split(" ")) {
            Tag tag = Tag.builder()
                    .tag(str)
                    .build();
            tags.add(tag);
        }
        return Photo.builder()
                .externalId(photoRequestDto.getExternalId())
                .author(photoRequestDto.getAuthor())
                .camera(photoRequestDto.getCamera())
                .tags(tags)
                .croppedPicture(photoRequestDto.getCroppedPicture())
                .fullPicture(photoRequestDto.getFullPicture())
                .build();
    }

    public PhotoResponseDto mapPhotoToResponseDto(Photo photo) {
        return PhotoResponseDto.builder()
                .author(photo.getAuthor())
                .camera(photo.getCamera())
                .externalId(photo.getExternalId())
                .croppedPicture(photo.getCroppedPicture())
                .fullPicture(photo.getFullPicture())
                .tags(photo.getTags().stream()
                        .map(tagMapper::mapTagToResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
