package com.javanautas.fakeapius.infrastructure.message.consumer;

import com.javanautas.fakeapius.apiv1.dto.ProductsDTO;
import com.javanautas.fakeapius.business.service.ProdutoService;
import com.javanautas.fakeapius.infrastructure.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FakeApiConsumerTest {

    @InjectMocks
    FakeApiConsumer consumer;

    @Mock
    ProdutoService service;

    @Test
    void deveReceberMensagemProdutoDTOComSucesso(){
        ProductsDTO produtoDTO = ProductsDTO.builder().descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();

       doNothing().when(service).salvaProdutoConsumer(produtoDTO);

       consumer.recebeProdutosDTO(produtoDTO);

       verify(service).salvaProdutoConsumer(produtoDTO);
       verifyNoMoreInteractions(service);
    }

    @Test
    void deveGerarExceptionCasoErroNoConsumer(){
        ProductsDTO produtoDTO = ProductsDTO.builder().descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();

        doThrow(new RuntimeException("Erro ao consumir mensagem")).when(service).salvaProdutoConsumer(produtoDTO);

        BusinessException e = assertThrows(BusinessException.class, () -> consumer.recebeProdutosDTO(produtoDTO));

        assertThat(e.getMessage(), is("Erro ao consumir mensagem do kafka "));
        verify(service).salvaProdutoConsumer(produtoDTO);
        verifyNoMoreInteractions(service);
    }
}
