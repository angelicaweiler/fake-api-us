package com.javanautas.fakeapius.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "ProdutoEntity")
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoEntity {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "title", length = 800)
    private String nome;
    @Column(name = "price")
    private BigDecimal preco;
    @Column(name = "category", length = 800)
    private String categoria;
    @Column(name = "description", length = 800)
    private String descricao;
    @Column(name = "image", length = 800)
    private String imagem;
    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusao;
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
