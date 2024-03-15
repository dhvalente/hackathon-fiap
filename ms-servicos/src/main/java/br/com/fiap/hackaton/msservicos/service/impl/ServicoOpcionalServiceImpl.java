package br.com.fiap.hackaton.msservicos.service.impl;

import br.com.fiap.hackaton.msservicos.service.IServicoOpcionalService;
import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalRequest;
import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalResponse;
import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import br.com.fiap.hackaton.msservicos.repository.ServicoOpcionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Service
@AllArgsConstructor
public class ServicoOpcionalServiceImpl implements IServicoOpcionalService {

    private ServicoOpcionalRepository repository;


    @Override
    public Page<ServicoOpcionalResponse> listarTodos(Pageable paginacao) {

        Page<ServicoOpcional> servicosEopcionais = repository.findAll(paginacao);


        Page<ServicoOpcionalResponse> dtoPage = servicosEopcionais.map(new Function<ServicoOpcional, ServicoOpcionalResponse>() {
            @Override
            public ServicoOpcionalResponse apply(ServicoOpcional entity) {
                ServicoOpcionalResponse dto = new ServicoOpcionalResponse(entity);


                return dto;
            }
        });

        return dtoPage;

        //return servicosEopcionais;
    }

    @Override
    public ServicoOpcionalResponse adicionar(ServicoOpcionalRequest servicoOpcionalRequest) {


        ServicoOpcional servicoOpcional = new ServicoOpcional(servicoOpcionalRequest);
        return new ServicoOpcionalResponse(repository.save(servicoOpcional));
    }

    @Override
    public ServicoOpcionalResponse atualizar(Long id, ServicoOpcionalRequest servicoOpcionalRequest) {

        ServicoOpcional servicoOpcional = repository.findById(id).orElseThrow(
                () ->  new RuntimeException("Registro nao existe!"));

        servicoOpcional.setNome(servicoOpcionalRequest.getNome());
        servicoOpcional.setTipo(servicoOpcionalRequest.getTipo());
        servicoOpcional.setValor(servicoOpcionalRequest.getValor());


        return new ServicoOpcionalResponse(repository.save(servicoOpcional));
    }

    @Override
    public void remover(Long id) {

        ServicoOpcional servicoOpcionalLocalizado = repository.findById(id).orElseThrow(
                () ->  new RuntimeException("Registro nao existe!"));

         repository.deleteById(id);
    }

    @Override
    public ServicoOpcionalResponse buscarPorId(Long id) {

        ServicoOpcional servicoOpcionalLocalizado = repository.findById(id).orElseThrow(
                () ->  new RuntimeException("Registro nao existe!"));

        return new ServicoOpcionalResponse(servicoOpcionalLocalizado);
    }

    @Override
    public List<ServicoOpcionalResponse> buscarPorNome(String nome) {
        var servicosEopcionais = repository.findByNomeLike(nome);
        List<ServicoOpcionalResponse> servicoOpcionalResponses = new ArrayList<>();
        servicosEopcionais.forEach(sp -> servicoOpcionalResponses.add(new ServicoOpcionalResponse(sp)));
        return servicoOpcionalResponses;
    }

}
