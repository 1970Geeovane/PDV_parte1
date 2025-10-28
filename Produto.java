public class Produto {
    private int idProduto;
    private String nome;
    private String unidade;
    private double quantidadeEstoque;

    // Construtor para inclusão (sem ID, pois é gerado pelo banco)
    public Produto(String nome, String unidade, double quantidadeEstoque) {
        this.nome = nome;
        this.unidade = unidade;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Construtor vazio para ser usado pelo DAO ao mapear do ResultSet
    public Produto() {
    }

    // Getters e Setters
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(double quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}