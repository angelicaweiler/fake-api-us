package com.javanautas.fakeapius.infrastructure.message.consumer;

import com.javanautas.fakeapius.apiv1.dto.ProductsDTO;
import com.javanautas.fakeapius.business.service.ProdutoService;
import com.javanautas.fakeapius.infrastructure.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FakeApiConsumer {

    private final ProdutoService produtoService;

    @KafkaListener(topics = "${topico.fake-api.consumer.nome}", groupId = "${topico.fake-api.consumer.group-id}")
    public void recebeProdutosDTO(ProductsDTO productsDTO) {
        try{
            produtoService.salvaProdutoConsumer(productsDTO);
        } catch (Exception exception) {
            throw new BusinessException("Erro ao consumir mensagem do kafka ");
        }
    }
}
