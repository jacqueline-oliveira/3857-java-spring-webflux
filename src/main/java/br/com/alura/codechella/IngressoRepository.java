package br.com.alura.codechella;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IngressoRepository extends ReactiveCrudRepository<Ingresso, Long> {
}
