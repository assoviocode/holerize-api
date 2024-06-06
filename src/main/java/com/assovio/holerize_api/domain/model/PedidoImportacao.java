package com.assovio.holerize_api.domain.model;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pedido_importacao")
@SQLRestriction(value = "deleted_at is null")
public class PedidoImportacao {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;

    private String senha;

    @Enumerated(EnumType.STRING)
    private EnumStatusImportacao status;

    private String log;

    @CreationTimestamp
	@Column(name = "created_at")
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at")
	private OffsetDateTime deleteAt;
}
