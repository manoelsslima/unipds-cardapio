package mx.florinda.cardapio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Database database = new Database();
        List<ItemCardapio> itens = database.listaDeItensCardapio();
        for (ItemCardapio item : itens) {
           System.out.println(item);
        }
        System.out.println("------");
        Set<ItemCardapio.CategoriaCardapio> categorias = new HashSet<>();
        for  (ItemCardapio item : database.listaDeItensCardapio()) {
            ItemCardapio.CategoriaCardapio categoria = item.categoria();
            categorias.add(categoria);
        }

        categorias.forEach(System.out::println);

        itens.stream().map(ItemCardapio::categoria).forEach(System.out::println);
    }
}
