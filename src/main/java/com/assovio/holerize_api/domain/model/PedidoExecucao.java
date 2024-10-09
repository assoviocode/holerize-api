package com.assovio.holerize_api.domain.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;

import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedido_execucao")
@SQLRestriction(value = "deleted_at is null")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class PedidoExecucao extends TimeStamp {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_importacao_id", nullable = false)
    private PedidoImportacao pedidoImportacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnumStatusImportacao status;

    @Column(name = "log")
    private String log;

    @Column(name = "tipo_erro")
    @Enumerated(EnumType.STRING)
    private EnumErrorType tipoErro;

    @Column(name = "mes_de")
    private Integer mesDe;

    @Column(name = "ano_de")
    private Integer anoDe;

    @Column(name = "mes_ate")
    private Integer mesAte;

    @Column(name = "ano_ate")
    private Integer anoAte;

    public PedidoExecucao(PedidoImportacao pedidoImportacao) {
        super();
        setPedidoImportacao(pedidoImportacao);
        setMesDe(pedidoImportacao.getMesDe());
        setAnoDe(pedidoImportacao.getAnoDe());
        setMesAte(pedidoImportacao.getMesAte());
        setAnoAte(pedidoImportacao.getAnoAte());
    }
}
