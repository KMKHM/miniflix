package com.system.miniflix.domain.video.service;

import com.system.miniflix.domain.video.dto.VideoResponse;
import com.system.miniflix.domain.video.entity.Video;
import com.system.miniflix.domain.video.repository.VideoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;

    @Test
    @DisplayName("전체 영상 목록 조회")
    void getVideos_success() {
        // given
        Video video1 = Video.builder()
                .title("테스트 영상1")
                .description("설명1")
                .s3Key("key1")
                .duration(120L)
                .build();
        Video video2 = Video.builder()
                .title("테스트 영상2")
                .description("설명2")
                .s3Key("key2")
                .duration(180L)
                .build();

        given(videoRepository.findByStatus(Video.VideoStatus.ACTIVE))
                .willReturn(List.of(video1, video2));

        // when
        List<VideoResponse> result = videoService.getVideos();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("테스트 영상1");
        assertThat(result.get(1).getTitle()).isEqualTo("테스트 영상2");
    }

    @Test
    @DisplayName("영상 단건 조회 성공")
    void getVideo_success() {
        // given
        Video video = Video.builder()
                .title("테스트 영상")
                .description("설명")
                .s3Key("key")
                .duration(120L)
                .build();

        given(videoRepository.findById(1L)).willReturn(Optional.of(video));

        // when
        VideoResponse result = videoService.getVideo(1L);

        // then
        assertThat(result.getTitle()).isEqualTo("테스트 영상");
        assertThat(result.getDescription()).isEqualTo("설명");
    }

    @Test
    @DisplayName("영상 단건 조회 실패 - 존재하지 않는 영상")
    void getVideo_fail_not_found() {
        // given
        given(videoRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> videoService.getVideo(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 영상입니다.");
    }
}