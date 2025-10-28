import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema PDV - Tela Principal");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        // Botão para abrir o cadastro de produtos
        JButton btnCadastroProdutos = new JButton("Cadastro de Produtos");
        btnCadastroProdutos.setPreferredSize(new Dimension(200, 50));

        add(btnCadastroProdutos);

        // Ação do botão
        btnCadastroProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria e exibe a tela de cadastro de produtos
                new TelaCadastroProdutos().setVisible(true);
            }
        });
    }
}