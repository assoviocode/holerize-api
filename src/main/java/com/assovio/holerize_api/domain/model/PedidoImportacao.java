package com.assovio.holerize_api.domain.model;

import java.util.Set;

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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PedidoImportacao extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnumStatusImportacao status;

    @Column(name = "log")
    private String log;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_erro")
    private EnumErrorType tipoErro;

    @Column(name = "mes_de")
    private Integer mesDe;

    @Column(name = "ano_de")
    private Integer anoDe;

    @Column(name = "mes_ate")
    private Integer mesAte;

    @Column(name = "ano_ate")
    private Integer anoAte;

    @Column(name = "mes_de_baixado")
    private Integer mesDeBaixado;

    @Column(name = "ano_de_baixado")
    private Integer anoDeBaixado;

    @Column(name = "mes_ate_baixado")
    private Integer mesAteBaixado;

    @Column(name = "ano_ate_baixado")
    private Integer anoAteBaixado;

    @Column(name = "quantidade_anos_solicitados", nullable = false)
    private Integer quantidadeAnosSolicitados;

    @Column(name = "quantidade_anos_baixados")
    private Integer quantidadeAnosBaixados;

    @Column(name = "total_vinculos_baixados")
    private Integer totalVinculosBaixados;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "pedidoImportacao", cascade = CascadeType.ALL)
    private Set<PedidoExecucao> pedidosExecucao;

}
