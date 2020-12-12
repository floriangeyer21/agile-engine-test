package com.agile.engine.demo.domain.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoRequestDto {

    @SerializedName(value = "id")
    private String externalId;
    private String author;
    private String camera;

    @SerializedName(value = "cropped_picture")
    private String croppedPicture;

    @SerializedName(value = "full_picture")
    private String fullPicture;
    private String tags;
}

