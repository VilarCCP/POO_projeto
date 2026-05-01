package rh.modelo;

public class FuncionarioHorista extends Funcionario {
    private double valorHora;
    private int horasTrabalhadas;

    public FuncionarioHorista(int id, String nome, String depto, boolean ativo, double valorHora, int horasTrabalhadas) {
        super(id, nome, depto, ativo);
        if (valorHora < 0 || horasTrabalhadas < 0) {
            throw new IllegalArgumentException("Valor da hora e horas trabalhadas devem ser positivos.");
        }
        this.valorHora = valorHora;
        this.horasTrabalhadas = horasTrabalhadas;
    }

    @Override
    public double calcularPagamento() {
        return this.valorHora * this.horasTrabalhadas;
    }

    public double getValorHora() { return valorHora; }
    public void setValorHora(double valorHora) { this.valorHora = valorHora; }

    public int getHorasTrabalhadas() { return horasTrabalhadas; }
    public void setHorasTrabalhadas(int horasTrabalhadas) { this.horasTrabalhadas = horasTrabalhadas; }
}
