package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLDatabase implements Database {

    @Override
    public List<ItemCardapio> listaDeItensCardapio() {
        ArrayList<ItemCardapio> itens = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, categoria, preco, preco_promocional FROM item_cardapio";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardapio", "root", "senha123");
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                String categoriaString = rs.getString("categoria");
                BigDecimal preco = rs.getBigDecimal("preco");
                BigDecimal precoPromocional = rs.getBigDecimal("preco_promocional");
                ItemCardapio.CategoriaCardapio categoria = ItemCardapio.CategoriaCardapio.valueOf(categoriaString);
                ItemCardapio itemCardapio = new ItemCardapio(id, nome, descricao, categoria, preco, precoPromocional);
                itens.add(itemCardapio);
            }
            return itens;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int totalItensCardapio() {
        return 0;
    }

    @Override
    public void adicionaItemCardapio(ItemCardapio itemCardapio) {

    }

    @Override
    public Optional<ItemCardapio> itemCardapioPorId(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removerItemCardapio(Long idParaRemover) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean alterarPrecoItemCardapio(long itemId, BigDecimal novoPreco) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
