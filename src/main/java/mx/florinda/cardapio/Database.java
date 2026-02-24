package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.util.*;


public class Database {

    private final Map<Long, ItemCardapio> itensPorId = new HashMap<>();

    public Database() {
        ItemCardapio refrescoDoChaves = new ItemCardapio(1L, "Refresco do Chaves", """
                Suco de limão, que parece Tamarindo mas tem gosto de groselha
                """, ItemCardapio.CategoriaCardapio.BEBIDAS, new BigDecimal("2.99"), null);

        itensPorId.put(1L, refrescoDoChaves);

        ItemCardapio sanduichePresunto = new ItemCardapio(2L, "Sanduíche de Presunto", """
        O clássico sanduíche de presunto do Chaves, simples e saboroso
        """, ItemCardapio.CategoriaCardapio.PRATOS_PRINCIPAIS, new BigDecimal("5.50"), null);

        itensPorId.put(2L, sanduichePresunto);

        ItemCardapio churrosDaDonaFlorinda = new ItemCardapio(3L, "Churros da Dona Florinda", """
        Churros crocantes recheados com doce de leite cremoso
        """, ItemCardapio.CategoriaCardapio.SOBREMESA, new BigDecimal("4.75"), null);

        itensPorId.put(3L, churrosDaDonaFlorinda);

        ItemCardapio sucoDeTamarindo = new ItemCardapio(4L, "Suco de Tamarindo", """
        Suco de tamarindo bem gelado, especial da Vila
        """, ItemCardapio.CategoriaCardapio.BEBIDAS, new BigDecimal("3.25"), null);

        itensPorId.put(4L, sucoDeTamarindo);

        ItemCardapio tortaDeJamon = new ItemCardapio(5L, "Torta de Jamón", """
        Farta fatia de torta salgada recheada com jamón e queijo
        """, ItemCardapio.CategoriaCardapio.SOBREMESA, new BigDecimal("8.90"), null);

        itensPorId.put(5L, tortaDeJamon);
    }

    public List<ItemCardapio> listaDeItensCardapio () {
        return new ArrayList<>(itensPorId.values());
    }

    public Optional<ItemCardapio> itemCardapioPorId(long id) {
        return Optional.ofNullable(itensPorId.get(id));
    }

    public boolean removerItemCardapio(Long idParaRemover) {
        ItemCardapio removed = itensPorId.remove(idParaRemover);
        return removed != null;
    }

    public boolean alterarPrecoItemCardapio(long itemId, BigDecimal novoPreco) {
        ItemCardapio item = itensPorId.get(itemId);
        if (item == null) {
            return false;
        }
        ItemCardapio itemCardapio = item.atlerarPreco(novoPreco);
        itensPorId.put(itemId, itemCardapio); // substitui o item antigo pelo novo item com o preço atualizado
        return true;
    }
}
