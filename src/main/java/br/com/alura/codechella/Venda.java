package br.com.alura.codechella;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("vendas")
public class Venda {
    @Id
    private Long id;
    private Long ingressoId;
    private int total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIngressoId() {
        return ingressoId;
    }

    public void setIngressoId(Long ingressoId) {
        this.ingressoId = ingressoId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
