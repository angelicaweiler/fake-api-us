package com.javanautas.fakeapius.business.service;

import com.javanautas.fakeapius.apiv1.dto.ProductsDTO;
import com.javanautas.fakeapius.business.converter.ProdutoConverter;
import com.javanautas.fakeapius.infrastructure.client.FakeApiClient;
import com.javanautas.fakeapius.infrastructure.configs.error.NotificacaoErro;
import com.javanautas.fakeapius.infrastructure.exceptions.BusinessException;
import com.javanautas.fakeapius.infrastructure.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class FakeApiService {

    private final FakeApiClient cliente;
    private final ProdutoConverter converter;
    private final ProdutoService produtoService;

    @NotificacaoErro
    public List<ProductsDTO> buscaProdutos() {
        try {


            List<ProductsDTO> dto = cliente.buscaListaProdutos();
            dto.forEach(produto -> {
                        Boolean retorno = produtoService.existsPorNome(produto.getNome());
                        if (retorno.equals(false)) {
                            produtoService.salvaProdutos(converter.toEntity(produto));
                        } else {
                            throw new ConflictException("Produto já existente no banco de dados " + produto.getNome());
                        }
                    }

            );
            return produtoService.buscaTodosProdutos();
        }catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar e gravar produtos no banco de dados");
        }
    }
}
