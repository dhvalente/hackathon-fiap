package br.com.fiap.hackaton.msservicos.service;

import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalRequest;
import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalResponse;
import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IServicoOpcionalService {

    public Page<ServicoOpcionalResponse> listarTodos(Pageable paginacao);

    @Transactional
    public ServicoOpcionalResponse adicionar(ServicoOpcionalRequest servicoOpcionalRequest);

    @Transactional
    public ServicoOpcionalResponse atualizar(Long id, ServicoOpcionalRequest servicoOpcionalRequest) throws Exception;

    @Transactional
    public void remover(Long id);

    public ServicoOpcionalResponse buscarPorId(Long id);

    public List<ServicoOpcionalResponse> buscarPorNome(String nome);

}
