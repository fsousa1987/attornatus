package com.github.fsousa1987.attornatus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_endereco")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EnderecoEntity {

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

}
