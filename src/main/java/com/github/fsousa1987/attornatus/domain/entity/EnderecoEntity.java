package com.github.fsousa1987.attornatus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_endereco")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EnderecoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5175171890280255216L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;

    @Column(name = "principal")
    private Boolean isPrincipal;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaEntity pessoa;

}
