package com.github.fsousa1987.attornatus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_pessoa")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PessoaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -5646824031655196611L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @OneToMany(
            mappedBy = "pessoa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EnderecoEntity> enderecos = new ArrayList<>();

}
