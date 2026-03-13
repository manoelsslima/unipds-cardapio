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
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardapio", "root", "senha123");
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery(sql)) {
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
        String sql = "SELECT COUNT(*) as total FROM item_cardapio";
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardapio", "root", "senha123");
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery(sql)) {
            int total = 0;
            if (rs.next()) {
                total = rs.getInt(1); // o índice começa com 1
            }
            return total;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void adicionaItemCardapio(ItemCardapio itemCardapio) {
        String sql = "INSERT INTO item_cardapio (nome, descricao, categoria, preco, preco_promocional) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardapio", "root", "senha123");
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, itemCardapio.nome());
            ps.setString(2, itemCardapio.descricao());
            ps.setString(3, itemCardapio.categoria().name());
            ps.setBigDecimal(4, itemCardapio.preco());
            ps.setBigDecimal(5, itemCardapio.precoComDesconto());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
