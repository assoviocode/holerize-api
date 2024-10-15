package com.assovio.holerize_api.domain.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.assovio.holerize_api.domain.model.Enums.EnumRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
@SQLRestriction(value = "deleted_at is null")
@DynamicInsert
@DynamicUpdate
public class Usuario extends TimeStamp implements UserDetails {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumRole role;

    private String email;

    private String login;

    private String nome;

    private String senha;

    private Integer creditos;

    @Column(name = "profile_image")
    private byte[] profileImage;

    @OneToMany(mappedBy = "usuario")
    private List<PedidoImportacao> pedidosImportacao;

    public Integer getCreditos() {
        Integer creditoGasto = 0;

        for (PedidoImportacao pedidoImportacao : pedidosImportacao) {

            switch (pedidoImportacao.getStatus()) {
                case NA_FILA:
                    creditoGasto += pedidoImportacao.getQuantidadeAnosSolicitados();
                case EM_ANDAMENTO:
                    creditoGasto += pedidoImportacao.getQuantidadeAnosSolicitados();
                case CONCLUIDO:
                    creditoGasto += Objects.requireNonNullElse(pedidoImportacao.getQuantidadeAnosBaixados(), 0);
                default:
                    break;
            }
        }

        return this.creditos - creditoGasto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch (role) {
            case BOT:
                return List.of(new SimpleGrantedAuthority("ROLE_BOT"));
            default:
                return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

}
