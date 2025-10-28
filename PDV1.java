import javax.swing.SwingUtilities;

/**
 *
 * @author geova
 */
public class PDV1 {
    public static void main(String[] args) {
        // Inicia a interface gráfica na thread de eventos do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
}