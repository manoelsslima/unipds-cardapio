package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface Database {
    List<ItemCardapio> listaDeItensCardapio();

    Optional<ItemCardapio> itemCardapioPorId(long id);

    boolean removerItemCardapio(Long idParaRemover);

    boolean alterarPrecoItemCardapio(long itemId, BigDecimal novoPreco);

    int totalItensCardapio();

    void adicionaItemCardapio(ItemCardapio itemCardapio);
}
