package br.com.alura.codechella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IngressoService {
    @Autowired
    private IngressoRepository repositorio;

    @Autowired
    private VendaRepository vendaRepository;

    public Flux<IngressoDto> obterTodos() {
        return  repositorio.findAll()
                .map(IngressoDto::toDto);
    }

    public Mono<IngressoDto> obterPorId(Long id) {
        return  repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(IngressoDto::toDto);
    }

    public Mono<IngressoDto> cadastrar(IngressoDto dto) {
        return repositorio.save(dto.toEntity())
                .map(IngressoDto::toDto);
    }

    public Mono<Void> excluir(Long id) {
        return repositorio.findById(id)
                .flatMap(repositorio::delete);
    }

    public Mono<IngressoDto> alterar(Long id, IngressoDto dto) {
        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                .flatMap(ingresso -> {
                    ingresso.setEventoId(dto.eventoId());
                    ingresso.setTipo(dto.tipo());
                    ingresso.setValor(dto.valor());
                    ingresso.setTotal(dto.total());
                    return repositorio.save(ingresso);
                })
                .map(IngressoDto::toDto);
    }

    @Transactional
    public Mono<IngressoDto> comprar(CompraDto dto) {
        return repositorio.findById(dto.ingressoId())
                .flatMap(ingresso -> {
                    Venda venda = new Venda();
                    venda.setIngressoId(ingresso.getId());
                    venda.setTotal(dto.total());
                    return vendaRepository.save(venda).then(Mono.defer(() -> {
                        ingresso.setTotal(ingresso.getTotal() - dto.total());
                        return repositorio.save(ingresso);
                    }));
                }).map(IngressoDto::toDto);
    }
}