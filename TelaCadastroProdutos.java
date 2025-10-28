import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



public class TelaCadastroProdutos extends JFrame {

    private JTable tabelaProdutos;
    private DefaultTableModel tableModel;
    private ProdutoDAO produtoDAO;

    public TelaCadastroProdutos() {
        produtoDAO = new ProdutoDAO();
        setTitle("Cadastro de Produtos");
        setSize(800, 600);
        setLocationRelativeTo(null); // Centraliza na tela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela

        // Layout principal
        setLayout(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Unidade", "Estoque"}, 0);
        tabelaProdutos = new JTable(tableModel);
        add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnIncluir);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos botões
        btnIncluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormulario(null);
            }
        });
        
        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaProdutos.getSelectedRow();
                if (linhaSelecionada != -1) {
                    int idProduto = (int) tableModel.getValueAt(linhaSelecionada, 0);
                    // Busca o produto completo do banco de dados para garantir consistência
                    Produto produtoParaAlterar = produtoDAO.buscarPorId(idProduto);
                    if (produtoParaAlterar != null)
                        abrirFormulario(produtoParaAlterar);
                } else {
                    JOptionPane.showMessageDialog(TelaCadastroProdutos.this, "Selecione um produto para alterar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaProdutos.getSelectedRow();
                if (linhaSelecionada != -1) {
                    int idProduto = (int) tableModel.getValueAt(linhaSelecionada, 0);
                    int confirm = JOptionPane.showConfirmDialog(TelaCadastroProdutos.this, "Tem certeza que deseja excluir este produto?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            produtoDAO.excluir(idProduto);
                            carregarProdutos(); // Atualiza a tabela
                        } catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(TelaCadastroProdutos.this, "Erro ao excluir produto: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaCadastroProdutos.this, "Selecione um produto para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Carregar os dados na tabela
        carregarProdutos();
    }

    private void carregarProdutos() {
        // Limpa a tabela
        tableModel.setRowCount(0);
        try {
            List<Produto> produtos = produtoDAO.listar();
            for (Produto p : produtos) {
                tableModel.addRow(new Object[]{
                    p.getIdProduto(),
                    p.getNome(),
                    p.getUnidade(),
                    p.getQuantidadeEstoque()
                });
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormulario(Produto produto) {
        // Passa esta janela como "pai" para poder atualizá-la depois
        TelaProdutoForm form = new TelaProdutoForm(this, produto);
        form.setVisible(true);
    }
    
    // Método para ser chamado pelo formulário filho para atualizar a tabela
    public void atualizarTabela() {
        carregarProdutos();
    }
}
