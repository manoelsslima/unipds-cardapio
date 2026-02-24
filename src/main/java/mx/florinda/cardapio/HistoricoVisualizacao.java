package mx.florinda.cardapio;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class HistoricoVisualizacao {

    private final Database database;

    //private final Map<ItemCardapio, LocalDateTime> visualizacoes = new HashMap<>();
    private final Map<ItemCardapio, LocalDateTime> visualizacoes = new WeakHashMap<>();

    public HistoricoVisualizacao(Database database) {
        this.database = database;
    }

    public void registrarVisualizacao(Long itemId) {
        Optional<ItemCardapio> optionalItemCardapio = database.itemCardapioPorId(itemId);
        if (optionalItemCardapio.isEmpty()) {
            System.out.println("Item não encontrado: " + itemId);
            return;
        }
        ItemCardapio itemCardapio = optionalItemCardapio.get();
        LocalDateTime agora = LocalDateTime.now();
        visualizacoes.put(itemCardapio, agora);
        System.out.printf("'%s' visualizado em '%s'\n", itemCardapio.nome(), agora);
    }

    public void mostrartotalItensVisualizados() {
        System.out.println("\nTotal de itens visualizados: " + visualizacoes.size());
    }

    public void listaVisualizacoes() {
        if (visualizacoes.isEmpty()) {
            System.out.println("Nenhum item visualizado ainda.");
            return;
        }
        System.out.println("\nHistórico de visualizações:");
        visualizacoes.forEach((item, dataHora) -> {
            System.out.printf(" - %s em %s\n", item.nome(), dataHora);
        });
        System.out.println();
    }
}
