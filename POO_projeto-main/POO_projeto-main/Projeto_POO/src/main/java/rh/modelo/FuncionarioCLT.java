package rh.modelo;

public class FuncionarioCLT extends Funcionario {
    private double salarioBase;

    public FuncionarioCLT(int id, String nome, String depto, boolean ativo, double salarioBase) {
        super(id, nome, depto, ativo);
        if (salarioBase < 0) {
            throw new IllegalArgumentException("Salário base não pode ser negativo.");
        }
        this.salarioBase = salarioBase;
    }

    @Override
    public double calcularPagamento() {
        return this.salarioBase;
    }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
}
