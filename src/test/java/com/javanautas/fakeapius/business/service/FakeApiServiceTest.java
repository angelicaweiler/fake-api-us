package com.javanautas.fakeapius.business.service;

import com.javanautas.fakeapius.apiv1.dto.ProductsDTO;
import com.javanautas.fakeapius.business.converter.ProdutoConverter;
import com.javanautas.fakeapius.infrastructure.client.FakeApiClient;
import com.javanautas.fakeapius.infrastructure.entities.ProdutoEntity;
import com.javanautas.fakeapius.infrastructure.exceptions.BusinessException;
import com.javanautas.fakeapius.infrastructure.exceptions.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FakeApiServiceTest {

    @InjectMocks
    FakeApiService service;

    @Mock
    FakeApiClient client;

    @Mock
    ProdutoConverter converter;

    @Mock
    ProdutoService produtoService;

    @Test
    void deveBuscarProdutosEGravarComSucesso() {
        List<ProductsDTO> listaProdutosDTO = new ArrayList<>();
        ProductsDTO produtoDTO = ProductsDTO.builder().entityId("1245").nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();
        listaProdutosDTO.add(produtoDTO);
        ProdutoEntity produtoEntity = ProdutoEntity.builder().id("1245").nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();

        when(client.buscaListaProdutos()).thenReturn(listaProdutosDTO);
        when(produtoService.existsPorNome(produtoDTO.getNome())).thenReturn(false);
        when(converter.toEntity(produtoDTO)).thenReturn(produtoEntity);
        when(produtoService.salvaProdutos(produtoEntity)).thenReturn(produtoEntity);
        when(produtoService.buscaTodosProdutos()).thenReturn(listaProdutosDTO);

        List<ProductsDTO> listaProdutosDTORetorno = service.buscaProdutos();

        assertEquals(listaProdutosDTO, listaProdutosDTORetorno);
        verify(client).buscaListaProdutos();
        verify(produtoService).existsPorNome(produtoDTO.getNome());
        verify(converter).toEntity(produtoDTO);
        verify(produtoService).salvaProdutos(produtoEntity);
        verify(produtoService).buscaTodosProdutos();
        verifyNoMoreInteractions(client, produtoService, converter);

    }

    @Test
    void deveBuscarProdutosENaoGravarCasoRetornoTrue() {
        List<ProductsDTO> listaProdutosDTO = new ArrayList<>();
        ProductsDTO produtoDTO = ProductsDTO.builder().entityId("1245").nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();
        listaProdutosDTO.add(produtoDTO);

        when(client.buscaListaProdutos()).thenReturn(listaProdutosDTO);
        when(produtoService.existsPorNome(produtoDTO.getNome())).thenReturn(true);

        ConflictException e = assertThrows(ConflictException.class, () -> service.buscaProdutos());

        assertThat(e.getMessage(), is("Produto já existente no banco de dados Jaqueta Vermelha"));
        verify(client).buscaListaProdutos();
        verify(produtoService).existsPorNome(produtoDTO.getNome());
        verifyNoMoreInteractions(client, produtoService);
        verifyNoInteractions(converter);


    }

    @Test
    void deveGerarExceptionCasoErroAoBuscarProdutosViaClient() {

        when(client.buscaListaProdutos()).thenThrow(new RuntimeException("Erro ao buscar Lista de Produtos"));

        BusinessException e = assertThrows(BusinessException.class, () -> service.buscaProdutos());

        assertThat(e.getMessage(), is("Erro ao buscar e gravar produtos no banco de dados"));
        verify(client).buscaListaProdutos();
        verifyNoMoreInteractions(client);
        verifyNoInteractions(converter, produtoService);


    }

}
