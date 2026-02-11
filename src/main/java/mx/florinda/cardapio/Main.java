package mx.florinda.cardapio;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Database database = new Database();

        // preciso de um histórico de visualização do cardápio
        HistoricoVisualizacao historicoVisualizacao = new HistoricoVisualizacao(database);
        historicoVisualizacao.registrarVisualizacao(1L);
        historicoVisualizacao.registrarVisualizacao(2L);
        historicoVisualizacao.registrarVisualizacao(4L);
        historicoVisualizacao.registrarVisualizacao(6L);

        historicoVisualizacao.mostrartotalItensVisualizados();
        historicoVisualizacao.listaVisualizacoes();

        Long idParaRemover = 1L;
        boolean removido = database.removerItemCardapio(idParaRemover);
        if (removido) {
            System.out.println("Item removido: " + idParaRemover);
        } else {
            System.out.println("Item não encontrado para remoção: " + idParaRemover);
        }
        database.listaDeItensCardapio().forEach(System.out::println);

//        List<ItemCardapio> itens = database.listaDeItensCardapio();
//        itens.forEach(System.out::println);
//
//        System.out.println("-----------------");
//
//        Optional<ItemCardapio> optionalItemCardapio = database.itemCardapioPorId(1L);
//        String mensagem = optionalItemCardapio.map(ItemCardapio::toString).orElse("Não encontrado");
//        System.out.println(mensagem);
//
//        System.out.println("=================");

//        Set<ItemCardapio.CategoriaCardapio> categoriasPromocao = new TreeSet<>();
//        categoriasPromocao.add(ItemCardapio.CategoriaCardapio.SOBREMESA);
//        categoriasPromocao.add(ItemCardapio.CategoriaCardapio.ENTRADA);
//        categoriasPromocao.forEach(System.out::println);
//
//        System.out.println("-----------------");
//
//        Set<ItemCardapio.CategoriaCardapio> categoriaCardapios2 = Set.of(ItemCardapio.CategoriaCardapio.SOBREMESA, ItemCardapio.CategoriaCardapio.ENTRADA);
//        categoriaCardapios2.forEach(System.out::println);
//        // categoriaCardapios2.add(ItemCardapio.CategoriaCardapio.BEBIDAS); Set.of() é imutável, não é possível adicionar novos elementos
//
//        System.out.println("-----------------");
//
//        // manter as categorias que estão em promoção
//        Set<ItemCardapio.CategoriaCardapio> categoriaCardapios3 = EnumSet.of(ItemCardapio.CategoriaCardapio.SOBREMESA, ItemCardapio.CategoriaCardapio.ENTRADA);
////        categoriaCardapios3.add(ItemCardapio.CategoriaCardapio.BEBIDAS);
//        categoriaCardapios3.forEach(System.out::println); // EnumSet é mutável, é possível adicionar novos elementos
//        System.out.println("-----------------");
//        // preciso das descrições associadas às categorias em promoção
//        Map<ItemCardapio.CategoriaCardapio, String> promocoes = new EnumMap<>(ItemCardapio.CategoriaCardapio.class);
//        promocoes.put(ItemCardapio.CategoriaCardapio.SOBREMESA, "O doce perfeito para você!");
//        promocoes.put(ItemCardapio.CategoriaCardapio.ENTRADA, "Comece susa refeição com um toque de sabor!");
//        System.out.println(promocoes);
    }
}
