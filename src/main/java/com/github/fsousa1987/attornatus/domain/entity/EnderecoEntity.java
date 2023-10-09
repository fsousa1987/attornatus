package com.github.fsousa1987.attornatus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_endereco")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EnderecoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5175171890280255216L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    private String logradouro;

    @EqualsAndHashCode.Include
    private String cep;

    @EqualsAndHashCode.Include
    private Integer numero;

    @EqualsAndHashCode.Include
    private String cidade;

    @Column(name = "principal")
    private boolean isPrincipal;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaEntity pessoa;

    public void desassociarEnderecoPrincipal() {
        this.isPrincipal = false;
    }

    public void associarEnderecoPrincipal() {
        this.isPrincipal = true;
    }

}
