package rh.modelo;

public class FuncionarioPJ extends Funcionario {
    private double valorContrato;

    public FuncionarioPJ(int id, String nome, String depto, boolean ativo, double valorContrato) {
        super(id, nome, depto, ativo);
        if (valorContrato < 0) {
            throw new IllegalArgumentException("Valor do contrato não pode ser negativo.");
        }
        this.valorContrato = valorContrato;
    }

    @Override
    public double calcularPagamento() {
        return this.valorContrato;
    }

    public double getValorContrato() { return valorContrato; }
    public void setValorContrato(double valorContrato) { this.valorContrato = valorContrato; }
}
