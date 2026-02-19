package com.system.miniflix.domain.video.repository;

import com.system.miniflix.domain.video.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByStatus(Video.VideoStatus status);
}
