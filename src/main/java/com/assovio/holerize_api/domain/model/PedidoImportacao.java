package com.assovio.holerize_api.domain.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;

import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedido_importacao")
@SQLRestriction(value = "deleted_at is null")
@DynamicInsert
@DynamicUpdate
public class PedidoImportacao extends TimeStamp {

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

    @Column(name = "mes_de", nullable = false)
    private Integer mesDe;

    @Column(name = "ano_de", nullable = false)
    private Integer anoDe;

    @Column(name = "mes_ate", nullable = false)
    private Integer mesAte;

    @Column(name = "ano_ate", nullable = false)
    private Integer anoAte;

    @Column(name = "quantidade_anos_baixados")
    private Integer quantidadeAnosBaixados;

    @Lob
    @Column(name = "file")
    private byte[] file;

}
