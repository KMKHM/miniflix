package com.system.miniflix.domain.video.entity;

import com.system.miniflix.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "videos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String s3Key;

    private String thumbnailKey;

    private Long duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoStatus status;

    @Builder
    public Video(String title, String description, String s3Key, String thumbnailKey, Long duration) {
        this.title = title;
        this.description = description;
        this.s3Key = s3Key;
        this.thumbnailKey = thumbnailKey;
        this.duration = duration;
        this.status = VideoStatus.ACTIVE;
    }

    public enum VideoStatus {
        ACTIVE, INACTIVE
    }
}
