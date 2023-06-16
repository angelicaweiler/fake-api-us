package com.javanautas.fakeapius.business.service;

import com.javanautas.fakeapius.apiv1.dto.ProductsDTO;
import com.javanautas.fakeapius.business.converter.ProdutoConverter;
import com.javanautas.fakeapius.infrastructure.entities.ProdutoEntity;
import com.javanautas.fakeapius.infrastructure.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoConverter converter;

    public ProdutoEntity salvaProdutos(ProdutoEntity entity) {
        try{
            return repository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar Produtos" + e);
        }
    }

    public ProductsDTO salvaProdutoDTO(ProductsDTO dto) {
        try{
            ProdutoEntity entity = converter.toEntity(dto);
            return converter.toDTO(repository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar Produtos" + e);
        }
    }


    public ProductsDTO buscaProdutoPorNome(String nome) {
        try {
            return converter.toDTO(repository.findByNome(nome));
        }catch (Exception e) {
            throw new RuntimeException(format("Erro ao buscar produto por nome", nome), e);
        }
    }

    public List<ProductsDTO> buscaTodosProdutos() {
        try {
            return converter.toListDTO(repository.findAll());
        }catch (Exception e) {
            throw new RuntimeException(format("Erro ao buscar todos os produtos"), e);
        }
    }

    public void deletaProduto(String nome){
        try{
            repository.deleteByNome(nome);
        }catch (Exception e) {
            throw new RuntimeException(format("Erro ao deletar produto por nome", nome), e);
        }
    }

    public Boolean existsPorNome(String nome) {
        try {
            return repository.existsByNome(nome);
        }catch (Exception e) {
            throw new RuntimeException(format("Erro ao buscar produto por nome", nome), e);
        }
    }

    public ProductsDTO updateProduto(String id, ProductsDTO dto){
        try{
            ProdutoEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Id não existe no banco de dados"));
            salvaProdutos(converter.toEntityUpdate(entity, dto, id));
            return converter.toDTO(repository.findByNome(entity.getNome()));
        }catch (Exception e) {
            throw new RuntimeException(format("Erro ao atualizar produto"), e);
        }
    }
}
