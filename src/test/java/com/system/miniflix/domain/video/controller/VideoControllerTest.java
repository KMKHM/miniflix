package com.system.miniflix.domain.video.controller;

import com.system.miniflix.domain.video.dto.VideoResponse;
import com.system.miniflix.domain.video.service.VideoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VideoController.class)
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VideoService videoService;

    @Test
    @DisplayName("전체 영상 목록 조회")
    @WithMockUser
    void getVideos_success() throws Exception {
        given(videoService.getVideos()).willReturn(List.of(
                new VideoResponse(1L, "테스트 영상1", "설명1", 120L),
                new VideoResponse(2L, "테스트 영상2", "설명2", 180L)
        ));

        mockMvc.perform(get("/api/videos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("테스트 영상1"))
                .andExpect(jsonPath("$[1].title").value("테스트 영상2"));
    }

    @Test
    @DisplayName("영상 단건 조회")
    @WithMockUser
    void getVideo_success() throws Exception {
        given(videoService.getVideo(1L))
                .willReturn(new VideoResponse(1L, "테스트 영상", "설명", 120L));

        mockMvc.perform(get("/api/videos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트 영상"));
    }

    @Test
    @DisplayName("스트림 URL 조회")
    @WithMockUser
    void getStreamUrl_success() throws Exception {
        given(videoService.getStreamUrl(1L)).willReturn("http://sample-stream-url/1");

        mockMvc.perform(get("/api/videos/1/stream"))
                .andExpect(status().isOk())
                .andExpect(content().string("http://sample-stream-url/1"));
    }
}