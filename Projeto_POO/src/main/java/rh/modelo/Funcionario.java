package rh.modelo;

public abstract class Funcionario {
    private int id;
    private String nome;
    private String depto;
    private boolean ativo;

    public Funcionario(int id, String nome, String depto, boolean ativo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.id = id;
        this.nome = nome;
        this.depto = depto;
        this.ativo = ativo;
    }

    // Método abstrato exigido
    public abstract double calcularPagamento();

    // Getters e Setters (Encapsulamento)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDepto() { return depto; }
    public void setDepto(String depto) { this.depto = depto; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
