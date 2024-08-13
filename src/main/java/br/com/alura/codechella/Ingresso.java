package br.com.alura.codechella;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("ingressos")
public class Ingresso {
    @Id
    private Long id;
    private Long eventoId;
    private TipoIngresso tipo;
    private Double valor;
    private int total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public TipoIngresso getTipo() {
        return tipo;
    }

    public void setTipo(TipoIngresso tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
