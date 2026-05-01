package rh.modelo;

public class EventoFolha {
    private int id;
    private int funcionarioId;
    private String tipoEvento; // "ADICIONAL" ou "DESCONTO"
    private String descricao;
    private double valor;

    public EventoFolha(int id, int funcionarioId, String tipoEvento, String descricao, double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor do evento não pode ser negativo.");
        }
        if (!tipoEvento.equalsIgnoreCase("ADICIONAL") && !tipoEvento.equalsIgnoreCase("DESCONTO")) {
            throw new IllegalArgumentException("Tipo de evento deve ser ADICIONAL ou DESCONTO.");
        }
        this.id = id;
        this.funcionarioId = funcionarioId;
        this.tipoEvento = tipoEvento.toUpperCase();
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}
