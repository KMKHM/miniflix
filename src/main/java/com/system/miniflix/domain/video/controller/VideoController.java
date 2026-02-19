package com.system.miniflix.domain.video.controller;

import com.system.miniflix.domain.video.dto.VideoResponse;
import com.system.miniflix.domain.video.service.VideoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<VideoResponse>> getVideos() {
        return ResponseEntity.ok(videoService.getVideos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getVideo(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideo(id));
    }

    @GetMapping("/{id}/stream")
    public ResponseEntity<String> getStreamUrl(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getStreamUrl(id));
    }
}
