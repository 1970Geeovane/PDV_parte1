import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO{
    // 1. Método para Inclusão
    public void incluir(Produto produto) {
        String sql = "INSERT INTO produto (nome, unidade, quantidade_estoque) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getUnidade());
            stmt.setDouble(3, produto.getQuantidadeEstoque());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Relança a exceção para a camada de visão (tela) poder tratar.
            throw new RuntimeException("Erro ao incluir produto: " + e.getMessage(), e);
        }
    }

    public void alterar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, unidade = ?, quantidade_estoque = ? WHERE id_produto = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getUnidade());
            stmt.setDouble(3, produto.getQuantidadeEstoque());
            stmt.setInt(4, produto.getIdProduto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar produto: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto: " + e.getMessage(), e);
        }
    }

    // 2. Método para Listar (para preencher a JTable)
    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }
        return produtos;
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProduto(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID: " + e.getMessage(), e);
        }
        return null; // Retorna null se não encontrar
    }

    /**
     * Método auxiliar para mapear uma linha do ResultSet para um objeto Produto.
     */
    private Produto mapearProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setIdProduto(rs.getInt("id_produto"));
        p.setNome(rs.getString("nome"));
        p.setUnidade(rs.getString("unidade"));
        p.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
        return p;
    }
    
}
