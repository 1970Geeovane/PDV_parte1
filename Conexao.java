import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    private static final Properties properties = new Properties();

    static {
        // Garante que o driver JDBC do MariaDB seja carregado e registrado.
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Lança uma exceção se o driver não for encontrado no classpath.
            throw new RuntimeException("Driver JDBC do MariaDB não encontrado! Verifique se o JAR está no projeto.", e);
        }

        // Carrega o arquivo de propriedades do classpath
        try (InputStream input = Conexao.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo de configuração 'db.properties' não encontrado no classpath.");
            }
            properties.load(input);
        } catch (IOException ex) {
            // Lança uma exceção em tempo de execução se não conseguir carregar as propriedades
            throw new RuntimeException("Erro ao carregar o arquivo de propriedades do banco de dados.", ex);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getProperty("db.url"), properties);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}
