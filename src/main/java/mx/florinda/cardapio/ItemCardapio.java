package mx.florinda.cardapio;

import java.math.BigDecimal;

public record ItemCardapio(Long id, String nome, String descricao, CategoriaCardapio categoria, BigDecimal preco,
                           BigDecimal precoComDesconto) {

    public ItemCardapio atlerarPreco(BigDecimal novoPreco) {
        if (novoPreco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preco deve ser maior que zero");
        }
        return new ItemCardapio(id, nome, descricao, categoria, novoPreco, precoComDesconto);
    }

    public enum CategoriaCardapio {
        ENTRADA, PRATOS_PRINCIPAIS, BEBIDAS, SOBREMESA
    }

}
