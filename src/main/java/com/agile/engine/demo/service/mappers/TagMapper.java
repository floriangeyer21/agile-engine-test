package com.agile.engine.demo.service.mappers;

import com.agile.engine.demo.domain.Tag;
import com.agile.engine.demo.domain.dto.TagResponseDto;
import org.springframework.stereotype.Service;

@Service
public class TagMapper {

    public TagResponseDto mapTagToResponseDto(Tag tag) {
        return TagResponseDto.builder()
                .tag(tag.getTag())
                .build();
    }
}
