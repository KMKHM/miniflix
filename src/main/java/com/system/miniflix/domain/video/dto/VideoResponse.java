package com.system.miniflix.domain.video.dto;

import com.system.miniflix.domain.video.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoResponse {
    private Long id;
    private String title;
    private String description;
    private Long duration;

    public static VideoResponse from(Video video) {
        return new VideoResponse(
                video.getId(),
                video.getTitle(),
                video.getDescription(),
                video.getDuration()
        );
    }
}
