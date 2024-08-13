package br.com.alura.codechella;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {
    private final IngressoService servico;
    private final Sinks.Many<IngressoDto> ingressoSink;

    public IngressoController(IngressoService servico) {
        this.servico = servico;
        this.ingressoSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping
    public Flux<IngressoDto> obterTodos() {
        return servico.obterTodos();
    }

    @GetMapping(value = "/{id}/disponivel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<IngressoDto> totalDisponivel(@PathVariable Long id) {
        return Flux.merge(servico.obterPorId(id), ingressoSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }

    @GetMapping("/{id}")
    public Mono<IngressoDto> obterPorId(@PathVariable Long id) {
        return servico.obterPorId(id);
    }

    @PostMapping
    public Mono<IngressoDto> cadastrar(@RequestBody IngressoDto dto) {
        return servico.cadastrar(dto);
    }

    @PostMapping("/compra")
    public Mono<IngressoDto> comprar(@RequestBody CompraDto dto) {
        return servico.comprar(dto)
                .doOnSuccess(i -> ingressoSink.tryEmitNext(i));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable Long id) {
        return servico.excluir(id);

    }

    @PutMapping("/{id}")
    public Mono<IngressoDto> alterar(@PathVariable Long id, @RequestBody IngressoDto dto){
        return servico.alterar(id, dto);
    }
}
