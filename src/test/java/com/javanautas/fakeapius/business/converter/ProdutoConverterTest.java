package com.javanautas.fakeapius.business.converter;

import com.javanautas.fakeapius.apiv1.dto.ProductsDTO;
import com.javanautas.fakeapius.infrastructure.entities.ProdutoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProdutoConverterTest {

    @InjectMocks
    ProdutoConverter converter;

    @Test
    void deveConverterParaProdutoEntityComSucesso(){
        ProductsDTO produtoDTO = ProductsDTO.builder().nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais").preco(new BigDecimal(500.00)).build();

        ProdutoEntity produtoEntityEsperado = ProdutoEntity.builder().id("1245").nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais").preco(new BigDecimal(500.00)).build();

        ProdutoEntity produtoEntity = converter.toEntity(produtoDTO);

        assertEquals(produtoEntityEsperado.getNome(), produtoEntity.getNome());
        assertEquals(produtoEntityEsperado.getCategoria(), produtoEntity.getCategoria());
        assertEquals(produtoEntityEsperado.getDescricao(), produtoEntity.getDescricao());
        assertEquals(produtoEntityEsperado.getImagem(), produtoEntity.getImagem());

        assertNotNull(produtoEntity.getId());
        assertNotNull(produtoEntity.getDataInclusao());

    }

    @Test
    void deveConverterParaProdutoEntityUpdateComSucesso(){
        ProductsDTO produtoDTO = ProductsDTO.builder().descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();
        ProdutoEntity produtoEntityEsperado = ProdutoEntity.builder().id("1245").nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();
        String id = "1245";
        ProdutoEntity entity = ProdutoEntity.builder().id("1245").nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais").preco(new BigDecimal(500.00)).build();

        ProdutoEntity produtoEntity = converter.toEntityUpdate(entity, produtoDTO, id);

        assertEquals(produtoEntityEsperado.getNome(), produtoEntity.getNome());
        assertEquals(produtoEntityEsperado.getCategoria(), produtoEntity.getCategoria());
        assertEquals(produtoEntityEsperado.getDescricao(), produtoEntity.getDescricao());
        assertEquals(produtoEntityEsperado.getImagem(), produtoEntity.getImagem());
        assertEquals(produtoEntityEsperado.getPreco(), produtoEntity.getPreco());
        assertEquals(produtoEntityEsperado.getId(), produtoEntity.getId());
        assertEquals(produtoEntityEsperado.getDataInclusao(), produtoEntity.getDataInclusao());

        assertNotNull(produtoEntity.getDataAtualizacao());

    }


    @Test
    void deveConverterParaListaProdutoDTOComSucesso(){

        List<ProductsDTO> listaProdutosDTO = new ArrayList<>();
        List<ProdutoEntity> listaProdutoEntity = new ArrayList<>();
        ProductsDTO produtoDTO = ProductsDTO.builder().entityId("1245").nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();
        listaProdutosDTO.add(produtoDTO);
        ProdutoEntity produtoEntityEsperado = ProdutoEntity.builder().id("1245").nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();
        listaProdutoEntity.add(produtoEntityEsperado);

        List<ProductsDTO> productoDTO = converter.toListDTO(listaProdutoEntity);

       assertEquals(listaProdutosDTO, productoDTO);


    }
}
