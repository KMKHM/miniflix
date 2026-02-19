package com.system.miniflix.domain.video.service;

import com.system.miniflix.domain.video.dto.VideoResponse;
import com.system.miniflix.domain.video.entity.Video;
import com.system.miniflix.domain.video.repository.VideoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public List<VideoResponse> getVideos() {
        return videoRepository.findByStatus(Video.VideoStatus.ACTIVE)
                .stream()
                .map(VideoResponse::from)
                .toList();
    }

    public VideoResponse getVideo(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영상입니다."));
        return VideoResponse.from(video);
    }

    public String getStreamUrl(Long id) {
        return "http://sample-stream-url/" + id;
    }
}
