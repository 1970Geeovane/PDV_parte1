
import javax.swing.*;
import java.awt.*;



public class TelaProdutoForm extends JDialog {

    private JTextField txtNome, txtQuantidade;
    private JComboBox<String> comboUnidade;
    private JButton btnOk, btnCancelar;

    private ProdutoDAO produtoDAO;
    private Produto produto; // null para inclusão, preenchido para alteração
    private TelaCadastroProdutos telaPai; // Referência para a tela de cadastro

    public TelaProdutoForm(TelaCadastroProdutos owner, Produto produto) {
        super(owner, true); // JDialog modal
        this.telaPai = owner;
        this.produto = produto;
        this.produtoDAO = new ProdutoDAO();

        setTitle(produto == null ? "Incluir Produto" : "Alterar Produto");
        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtNome = new JTextField(20);
        formPanel.add(txtNome, gbc);

        // Unidade
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Unidade:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        comboUnidade = new JComboBox<>(new String[]{"UN", "KG", "L"});
        formPanel.add(comboUnidade, gbc);

        // Quantidade
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Quantidade em Estoque:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtQuantidade = new JTextField();
        formPanel.add(txtQuantidade, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnOk = new JButton("OK");
        btnCancelar = new JButton("Cancelar");
        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancelar);
        add(buttonPanel, BorderLayout.SOUTH);

        // Preenche os campos se for alteração
        if (produto != null) {
            txtNome.setText(produto.getNome());
            comboUnidade.setSelectedItem(produto.getUnidade());
            txtQuantidade.setText(String.valueOf(produto.getQuantidadeEstoque()));
        }

        // Ações dos botões
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                salvar();
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose(); // Fecha o dialog
            }
        });
    }

    private void salvar() {
        try {
            String nome = txtNome.getText();
            String unidade = (String) comboUnidade.getSelectedItem();
            double quantidade = Double.parseDouble(txtQuantidade.getText().replace(',', '.'));

            if (nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome do produto não pode ser vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (produto == null) { // Inclusão
                Produto novoProduto = new Produto(nome, unidade, quantidade);
                produtoDAO.incluir(novoProduto);
            } else { // Alteração
                produto.setNome(nome);
                produto.setUnidade(unidade);
                produto.setQuantidadeEstoque(quantidade);
                produtoDAO.alterar(produto);
            }
            
            // Atualiza a tabela na tela de cadastro e fecha o formulário
            telaPai.atualizarTabela();
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "A quantidade em estoque deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }
}
