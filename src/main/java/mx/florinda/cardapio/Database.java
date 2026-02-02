package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


public class Database {
    public List<ItemCardapio> listaDeItensCardapio () {
        List<ItemCardapio> itens = new LinkedList<>();

        ItemCardapio refrescoDoChaves = new ItemCardapio(1L, "Refresco do Chaves", """
                Suco de limão, que parece Tamarindo mas tem gosto de groselha
                """, ItemCardapio.CategoriaCardapio.BEBIDAS, new BigDecimal("2.99"), null);

        itens.add(refrescoDoChaves);

        ItemCardapio sanduichePresunto = new ItemCardapio(2L, "Sanduíche de Presunto", """
        O clássico sanduíche de presunto do Chaves, simples e saboroso
        """, ItemCardapio.CategoriaCardapio.PRATOS_PRINCIPAIS, new BigDecimal("5.50"), null);

        itens.add(sanduichePresunto);

        ItemCardapio churrosDaDonaFlorinda = new ItemCardapio(3L, "Churros da Dona Florinda", """
        Churros crocantes recheados com doce de leite cremoso
        """, ItemCardapio.CategoriaCardapio.SOBREMESA, new BigDecimal("4.75"), null);

        itens.add(churrosDaDonaFlorinda);

        ItemCardapio sucoDeTamarindo = new ItemCardapio(4L, "Suco de Tamarindo", """
        Suco de tamarindo bem gelado, especial da Vila
        """, ItemCardapio.CategoriaCardapio.BEBIDAS, new BigDecimal("3.25"), null);

        itens.add(sucoDeTamarindo);

        ItemCardapio tortaDeJamon = new ItemCardapio(5L, "Torta de Jamón", """
        Farta fatia de torta salgada recheada com jamón e queijo
        """, ItemCardapio.CategoriaCardapio.SOBREMESA, new BigDecimal("8.90"), null);

        itens.add(tortaDeJamon);

        return itens;
    }
}
