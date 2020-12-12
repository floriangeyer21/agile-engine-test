package com.agile.engine.demo.domain.dto;

import com.google.gson.annotations.SerializedName;
import javax.persistence.ManyToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseDto {

    @SerializedName("id")
    private String externalId;
    private String author;
    private String camera;
    private String croppedPicture;
    private String fullPicture;

    @ManyToMany
    private List<TagResponseDto> tags;
}
