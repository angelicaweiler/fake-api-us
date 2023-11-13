package com.javanautas.fakeapius.apiv1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javanautas.fakeapius.apiv1.dto.FakeApiController;
import com.javanautas.fakeapius.apiv1.dto.ProductsDTO;
import com.javanautas.fakeapius.business.service.FakeApiService;
import com.javanautas.fakeapius.business.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FakeApiControllerTest {

    @InjectMocks
    FakeApiController controller;

    @Mock
    FakeApiService fakeApiService;

    @Mock
    ProdutoService produtoService;

    private MockMvc mockMvc;

    private String json;

    private String url;

    private ProductsDTO productsDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
        url = "/produtos";
        productsDTO = ProductsDTO.builder().nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais").preco(new BigDecimal(500.00)).build();
        json = objectMapper.writeValueAsString(productsDTO);
    }

    @Test
    void deveBuscarProdutosFakeApiESalvarComSucesso() throws Exception {

        when(fakeApiService.buscaProdutos()).thenReturn(Collections.singletonList(productsDTO));

        mockMvc.perform(post(url + "/api")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(fakeApiService).buscaProdutos();
        verifyNoMoreInteractions(fakeApiService);
    }

    @Test
    void deveSalvarProdutosDTOComSucesso() throws Exception {

        when(produtoService.salvaProdutoDTO(productsDTO)).thenReturn(productsDTO);

        mockMvc.perform(post(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(produtoService).salvaProdutoDTO(productsDTO);
        verifyNoMoreInteractions(produtoService);
    }


    @Test
    void naoDeveEnviarRequestCasoProdutosDTOSejaNull() throws Exception {

        mockMvc.perform(post(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


        verifyNoInteractions(produtoService);
    }
    @Test
    void deveAtualizarProdutosDTOComSucesso() throws Exception {

        String id = "1245";

        when(produtoService.updateProduto(id, productsDTO)).thenReturn(productsDTO);

        mockMvc.perform(put(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("id", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(produtoService).updateProduto(id, productsDTO);
        verifyNoMoreInteractions(produtoService);
    }


    @Test
    void naoDeveEnviarRequestCasoIDSejaNull() throws Exception {

        mockMvc.perform(put(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


        verifyNoInteractions(produtoService);
    }
    @Test
    void deveDeletarProdutosDTOComSucesso() throws Exception {

        String nome = "Jaqueta Vermelha";

        doNothing().when(produtoService).deletaProduto(nome);

        mockMvc.perform(delete(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("nome", nome)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        verify(produtoService).deletaProduto(nome);
        verifyNoMoreInteractions(produtoService);
    }


    @Test
    void naoDeveEnviarRequestDeleteCasoNomeSejaNull() throws Exception {

        mockMvc.perform(delete(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(produtoService);
    }
    @Test
    void deveBuscarProdutosDTOPorNomeComSucesso() throws Exception {

        String nome = "Jaqueta Vermelha";

        when(produtoService.buscaProdutoPorNome(nome)).thenReturn(productsDTO);

        mockMvc.perform(get(url + "/" + nome)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(produtoService).buscaProdutoPorNome(nome);
        verifyNoMoreInteractions(produtoService);
    }

}
