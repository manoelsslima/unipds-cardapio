package mx.florinda.cardapio;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Database database = new Database();
        // precisa alterar o preço de um item do cardápio

        ItemCardapio item = database.itemCardapioPorId(1L).orElseThrow();
        System.out.printf("\n%s (%d) R$ %s", item.nome(), item.id(), item.preco());
        boolean alterado = database.alterarPrecoItemCardapio(1L, new BigDecimal("3.99"));

        ItemCardapio itemAlterado = database.itemCardapioPorId(1L).orElseThrow();
        System.out.printf("\n%s (%d) R$ %s", itemAlterado.nome(), itemAlterado.id(), itemAlterado.preco());

        boolean alterado2 = database.alterarPrecoItemCardapio(1L, new BigDecimal("2.99"));

        database.alterarPrecoItemCardapio(1L, new BigDecimal("4.99"));
        System.out.printf("\n%s (%d) R$ %s", itemAlterado.nome(), itemAlterado.id(), itemAlterado.preco());

        // preciso auditar as mudanças de preços dos itens do cardápio
        database.imprimirRastroAuditoriaPreco();


//        // preciso de um histórico de visualização do cardápio
//        HistoricoVisualizacao historico = new HistoricoVisualizacao(database);
//        historico.registrarVisualizacao(1L);
//        historico.registrarVisualizacao(2L);
//        historico.registrarVisualizacao(4L);
//        historico.registrarVisualizacao(6L);
//
//        historico.mostrartotalItensVisualizados();
//        historico.listaVisualizacoes();
//
//        Long idParaRemover = 1L;
//        boolean removido = database.removerItemCardapio(idParaRemover);
//        if (removido) {
//            System.out.println("Item removido: " + idParaRemover);
//        } else {
//            System.out.println("Item não encontrado para remoção: " + idParaRemover);
//        }
//        database.listaDeItensCardapio().forEach(System.out::println);
//
//        System.out.println("Solicitando o GC...");
//        System.gc();
//        Thread.sleep(500); // Aguarda um pouco para garantir que o coletor de lixo tenha tempo de processar os objetos fracos
//
//        historico.mostrartotalItensVisualizados();
//        historico.listaVisualizacoes();

//        System.out.println("Solicitando o GC...");
//        System.gc();
//        Thread.sleep(500);

//        historicoVisualizacao.mostrartotalItensVisualizados();
//        historicoVisualizacao.listaVisualizacoes();

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
