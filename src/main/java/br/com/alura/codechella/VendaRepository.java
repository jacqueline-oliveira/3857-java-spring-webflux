package br.com.alura.codechella;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VendaRepository extends ReactiveCrudRepository<Venda, Long> {
}
