package rh;

import rh.dao.EventoFolhaDAO;
import rh.dao.FuncionarioDAO;
import rh.modelo.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static FuncionarioDAO funcionarioDAO;
    private static EventoFolhaDAO eventoDAO;

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de RH...");
        try {
            funcionarioDAO = new FuncionarioDAO();
            eventoDAO = new EventoFolhaDAO();
            System.out.println("Conexão com o banco de dados estabelecida.");
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            System.out.println("Verifique se o MySQL está rodando e se as credenciais (cliente / sem senha) estão corretas.");
            return;
        }

        int opcao = 0;
        do {
            System.out.println("\n--- Sistema de RH ---");
            System.out.println("1. Cadastrar Funcionário");
            System.out.println("2. Listar Funcionários");
            System.out.println("3. Atualizar Funcionário");
            System.out.println("4. Deletar Funcionário");
            System.out.println("5. Adicionar Evento de Folha (Adicional/Desconto)");
            System.out.println("6. Gerar Relatório: Folha do Mês por Funcionário");
            System.out.println("7. Gerar Relatório: Totais por Tipo de Funcionário");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    listarFuncionarios();
                    break;
                case 3:
                    atualizarFuncionario();
                    break;
                case 4:
                    deletarFuncionario();
                    break;
                case 5:
                    adicionarEvento();
                    break;
                case 6:
                    relatorioFolhaFuncionario();
                    break;
                case 7:
                    relatorioTotalPorTipo();
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 8);
    }

    private static void cadastrarFuncionario() {
        System.out.println("\n--- Cadastrar Funcionário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Departamento: ");
        String depto = scanner.nextLine();
        System.out.println("Tipos: 1. CLT | 2. PJ | 3. Horista");
        System.out.print("Escolha o tipo: ");
        int tipo = Integer.parseInt(scanner.nextLine());

        Funcionario f = null;
        if (tipo == 1) {
            System.out.print("Salário Base: R$ ");
            double salario = Double.parseDouble(scanner.nextLine());
            f = new FuncionarioCLT(0, nome, depto, true, salario);
        } else if (tipo == 2) {
            System.out.print("Valor do Contrato: R$ ");
            double valor = Double.parseDouble(scanner.nextLine());
            f = new FuncionarioPJ(0, nome, depto, true, valor);
        } else if (tipo == 3) {
            System.out.print("Valor da Hora: R$ ");
            double valorHora = Double.parseDouble(scanner.nextLine());
            System.out.print("Horas Trabalhadas: ");
            int horas = Integer.parseInt(scanner.nextLine());
            f = new FuncionarioHorista(0, nome, depto, true, valorHora, horas);
        } else {
            System.out.println("Tipo inválido.");
            return;
        }

        funcionarioDAO.adicionar(f);
        System.out.println("Funcionário cadastrado com sucesso! ID: " + f.getId());
    }

    private static void listarFuncionarios() {
        System.out.println("\n--- Lista de Funcionários ---");
        List<Funcionario> lista = funcionarioDAO.listarTodos();
        for (Funcionario f : lista) {
            System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome() + " | Tipo: " + f.getClass().getSimpleName() + " | Ativo: " + f.isAtivo());
        }
    }

    private static void atualizarFuncionario() {
        System.out.print("Digite o ID do funcionário a ser atualizado: ");
        int id = Integer.parseInt(scanner.nextLine());
        Funcionario f = funcionarioDAO.buscarPorId(id);
        
        if (f == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.print("Novo nome (" + f.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.trim().isEmpty()) f.setNome(nome);

        System.out.print("Novo departamento (" + f.getDepto() + "): ");
        String depto = scanner.nextLine();
        if (!depto.trim().isEmpty()) f.setDepto(depto);

        System.out.print("Ativo? (S/N) (" + (f.isAtivo() ? "S" : "N") + "): ");
        String ativo = scanner.nextLine();
        if (ativo.equalsIgnoreCase("S")) f.setAtivo(true);
        else if (ativo.equalsIgnoreCase("N")) f.setAtivo(false);

        // Atualizar campos específicos com polimorfismo
        if (f instanceof FuncionarioCLT) {
            System.out.print("Novo Salário Base (" + ((FuncionarioCLT) f).getSalarioBase() + "): ");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) ((FuncionarioCLT) f).setSalarioBase(Double.parseDouble(input));
        } else if (f instanceof FuncionarioPJ) {
            System.out.print("Novo Valor Contrato (" + ((FuncionarioPJ) f).getValorContrato() + "): ");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) ((FuncionarioPJ) f).setValorContrato(Double.parseDouble(input));
        } else if (f instanceof FuncionarioHorista) {
            System.out.print("Novo Valor Hora (" + ((FuncionarioHorista) f).getValorHora() + "): ");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) ((FuncionarioHorista) f).setValorHora(Double.parseDouble(input));
            
            System.out.print("Novas Horas Trabalhadas (" + ((FuncionarioHorista) f).getHorasTrabalhadas() + "): ");
            input = scanner.nextLine();
            if (!input.trim().isEmpty()) ((FuncionarioHorista) f).setHorasTrabalhadas(Integer.parseInt(input));
        }

        funcionarioDAO.atualizar(f);
        System.out.println("Funcionário atualizado com sucesso.");
    }

    private static void deletarFuncionario() {
        System.out.print("Digite o ID do funcionário a ser deletado: ");
        int id = Integer.parseInt(scanner.nextLine());
        funcionarioDAO.deletar(id);
        System.out.println("Funcionário deletado.");
    }

    private static void adicionarEvento() {
        System.out.print("Digite o ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        Funcionario f = funcionarioDAO.buscarPorId(id);
        if (f == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.print("Tipo do Evento (1. ADICIONAL | 2. DESCONTO): ");
        int tipo = Integer.parseInt(scanner.nextLine());
        String tipoStr = (tipo == 1) ? "ADICIONAL" : "DESCONTO";

        System.out.print("Descrição: ");
        String desc = scanner.nextLine();

        System.out.print("Valor: R$ ");
        double valor = Double.parseDouble(scanner.nextLine());

        EventoFolha e = new EventoFolha(0, id, tipoStr, desc, valor);
        eventoDAO.adicionar(e);
        System.out.println("Evento adicionado com sucesso.");
    }

    private static void relatorioFolhaFuncionario() {
        System.out.println("\n--- Relatório: Folha do Mês por Funcionário ---");
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
        
        for (Funcionario f : funcionarios) {
            if (!f.isAtivo()) continue;

            // POLIMORFISMO: Chamada de método com override obrigatório
            double pagamentoBase = f.calcularPagamento();
            double totalAdicionais = 0;
            double totalDescontos = 0;

            List<EventoFolha> eventos = eventoDAO.buscarPorFuncionario(f.getId());
            for (EventoFolha e : eventos) {
                if (e.getTipoEvento().equals("ADICIONAL")) {
                    totalAdicionais += e.getValor();
                } else if (e.getTipoEvento().equals("DESCONTO")) {
                    totalDescontos += e.getValor();
                }
            }

            double valorLiquido = pagamentoBase + totalAdicionais - totalDescontos;

            System.out.println("Funcionário: " + f.getNome() + " (ID: " + f.getId() + ")");
            System.out.println("  Tipo: " + f.getClass().getSimpleName());
            System.out.println("  Pagamento Base: R$ " + pagamentoBase);
            System.out.println("  Adicionais: R$ " + totalAdicionais);
            System.out.println("  Descontos: R$ " + totalDescontos);
            System.out.println("  Valor Líquido: R$ " + valorLiquido);
            System.out.println("-----------------------------------");
        }
    }

    private static void relatorioTotalPorTipo() {
        System.out.println("\n--- Relatório: Totais por Tipo de Funcionário ---");
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
        
        double totalCLT = 0, totalPJ = 0, totalHorista = 0;

        for (Funcionario f : funcionarios) {
            if (!f.isAtivo()) continue;
            
            double base = f.calcularPagamento();
            double adic = 0, desc = 0;
            
            for (EventoFolha e : eventoDAO.buscarPorFuncionario(f.getId())) {
                if (e.getTipoEvento().equals("ADICIONAL")) adic += e.getValor();
                else desc += e.getValor();
            }
            
            double liquido = base + adic - desc;

            if (f instanceof FuncionarioCLT) {
                totalCLT += liquido;
            } else if (f instanceof FuncionarioPJ) {
                totalPJ += liquido;
            } else if (f instanceof FuncionarioHorista) {
                totalHorista += liquido;
            }
        }

        System.out.println("Total Pago - CLT: R$ " + totalCLT);
        System.out.println("Total Pago - PJ: R$ " + totalPJ);
        System.out.println("Total Pago - HORISTA: R$ " + totalHorista);
        System.out.println("Total Geral: R$ " + (totalCLT + totalPJ + totalHorista));
    }
}
