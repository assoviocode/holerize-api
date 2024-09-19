package com.assovio.holerize_api.domain.model;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicInsert
@DynamicUpdate
public class PedidoImportacao {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnumStatusImportacao status = EnumStatusImportacao.NOVO;

    @Column(name = "log")
    private String log;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_erro")
    private EnumErrorType tipoErro;

    @Column(name = "ano_de", nullable = false)
    private Integer anoDe;

    @Column(name = "ano_ate", nullable = false)
    private Integer anoAte;

    @CreationTimestamp
    @Column(name = "created_at")
	private OffsetDateTime createdAt;

	@UpdateTimestamp
    @Column(name = "updated_at")
	private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
	private OffsetDateTime deleteAt;
}
