package com.assovio.holerize_api.domain.model;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TimeStamp {

    @CreationTimestamp
    @Column(name = "created_at")
	private OffsetDateTime createdAt;

	@UpdateTimestamp
    @Column(name = "updated_at")
	private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
	private OffsetDateTime deleteAt;
}
