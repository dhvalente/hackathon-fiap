package br.com.fiap.hackaton.customer.repository;

import br.com.fiap.hackaton.customer.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}