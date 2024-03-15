package br.com.fiap.hackaton.msservicos.repository;

import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoOpcionalRepository extends JpaRepository<ServicoOpcional, Long> {
    List<ServicoOpcional> findByNomeLike(@Param("servicoItem") String servicoItem);
}
