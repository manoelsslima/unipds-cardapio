package mx.florinda.cardapio;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        Database database = new Database();
        List<ItemCardapio> itens = database.listaDeItensCardapio();
        itens.forEach(System.out::println);
        System.out.println("-----------------");
        Optional<ItemCardapio> optionalItemCardapio = database.itemCardapioPorId(1L);
        optionalItemCardapio.ifPresent(itemCardapio -> System.out.println(itemCardapio));
    }
}
