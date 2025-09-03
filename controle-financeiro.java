import java.util.*;

// ===== CLASSE ABSTRATA CONTA =====
abstract class Conta {
    protected String numeroConta;
    protected double saldo;
    protected String titular;
    
    public Conta(String numeroConta, String titular) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = 0.0;
    }
    
    // Métodos abstratos (polimorfismo)
    public abstract void sacar(double valor);
    public abstract void depositar(double valor);
    
    // Métodos comuns (herança)
    public void transferir(double valor, Conta contaDestino) {
        if (this.saldo >= valor) {
            this.sacar(valor);
            contaDestino.depositar(valor);
            System.out.println("Transferência de R$ " + valor + " realizada com sucesso!");
        } else {
            System.out.println("Saldo insuficiente para transferência.");
        }
    }
    
    // Getters e Setters (encapsulamento)
    public double getSaldo() { return saldo; }
    public String getNumeroConta() { return numeroConta; }
    public String getTitular() { return titular; }
    
    public void setSaldo(double saldo) { this.saldo = saldo; }
}

// ===== CONTA CORRENTE =====
class ContaCorrente extends Conta {
    private double limiteChequeEspecial;
    
    public ContaCorrente(String numeroConta, String titular, double limiteChequeEspecial) {
        super(numeroConta, titular);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }
    
    @Override
    public void sacar(double valor) {
        double limiteTotal = saldo + limiteChequeEspecial;
        if (valor <= limiteTotal) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado. Saldo atual: R$ " + saldo);
        } else {
            System.out.println("Valor excede o limite disponível.");
        }
    }
    
    @Override
    public void depositar(double valor) {
        saldo += valor;
        System.out.println("Depósito de R$ " + valor + " realizado. Saldo atual: R$ " + saldo);
    }
    
    public double getLimiteChequeEspecial() { return limiteChequeEspecial; }
}

// ===== CONTA POUPANÇA =====
class ContaPoupanca extends Conta {
    private double taxaJuros;
    
    public ContaPoupanca(String numeroConta, String titular, double taxaJuros) {
        super(numeroConta, titular);
        this.taxaJuros = taxaJuros;
    }
    
    @Override
    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado. Saldo atual: R$ " + saldo);
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }
    
    @Override
    public void depositar(double valor) {
        saldo += valor;
        System.out.println("Depósito de R$ " + valor + " realizado. Saldo atual: R$ " + saldo);
    }
    
    public void aplicarJuros() {
        double juros = saldo * taxaJuros / 100;
        saldo += juros;
        System.out.println("Juros aplicados: R$ " + juros + ". Novo saldo: R$ " + saldo);
    }
    
    public double getTaxaJuros() { return taxaJuros; }
}

// ===== CLASSE INVESTIMENTO =====
class Investimento {
    private String tipo;
    private double valor;
    private double rentabilidade;
    private String dataInvestimento;
    
    public Investimento(String tipo, double valor, double rentabilidade, String dataInvestimento) {
        this.tipo = tipo;
        this.valor = valor;
        this.rentabilidade = rentabilidade;
        this.dataInvestimento = dataInvestimento;
    }
    
    public double calcularRendimento() {
        return valor * rentabilidade / 100;
    }
    
    public void mostrarDetalhes() {
        System.out.println("=== Detalhes do Investimento ===");
        System.out.println("Tipo: " + tipo);
        System.out.println("Valor Investido: R$ " + valor);
        System.out.println("Rentabilidade: " + rentabilidade + "%");
        System.out.println("Data: " + dataInvestimento);
        System.out.println("Rendimento: R$ " + calcularRendimento());
    }
    
    // Getters
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public double getRentabilidade() { return rentabilidade; }
    public String getDataInvestimento() { return dataInvestimento; }
}

// ===== CLASSE TRANSACAO =====
class Transacao {
    private String tipo;
    private double valor;
    private String data;
    private String descricao;
    
    public Transacao(String tipo, double valor, String data, String descricao) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
    }
    
    public void mostrarTransacao() {
        System.out.println(data + " | " + tipo + " | R$ " + valor + " | " + descricao);
    }
    
    // Getters
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public String getData() { return data; }
    public String getDescricao() { return descricao; }
}

// ===== REPOSITÓRIO =====
class RepositorioContas {
    private static List<Conta> contas = new ArrayList<>();
    private static List<Transacao> historicoTransacoes = new ArrayList<>();
    private static List<Investimento> investimentos = new ArrayList<>();
    
    // Métodos para Contas
    public static void adicionarConta(Conta conta) {
        contas.add(conta);
        System.out.println("Conta " + conta.getNumeroConta() + " criada com sucesso!");
    }
    
    public static Conta buscarConta(String numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }
    
    public static void listarContas() {
        System.out.println("\n=== CONTAS CADASTRADAS ===");
        for (Conta conta : contas) {
            String tipoConta = conta instanceof ContaCorrente ? "Corrente" : "Poupança";
            System.out.println("Conta: " + conta.getNumeroConta() + 
                             " | Titular: " + conta.getTitular() + 
                             " | Tipo: " + tipoConta + 
                             " | Saldo: R$ " + conta.getSaldo());
        }
    }
    
    // Métodos para Transações
    public static void adicionarTransacao(Transacao transacao) {
        historicoTransacoes.add(transacao);
    }
    
    public static void mostrarHistorico() {
        System.out.println("\n=== HISTÓRICO DE TRANSAÇÕES ===");
        for (Transacao transacao : historicoTransacoes) {
            transacao.mostrarTransacao();
        }
    }
    
    // Métodos para Investimentos
    public static void adicionarInvestimento(Investimento investimento) {
        investimentos.add(investimento);
        System.out.println("Investimento cadastrado com sucesso!");
    }
    
    public static void listarInvestimentos() {
        System.out.println("\n=== INVESTIMENTOS ===");
        for (Investimento inv : investimentos) {
            inv.mostrarDetalhes();
            System.out.println("---");
        }
    }
}

// ===== CLASSE PRINCIPAL (MUDANÇA AQUI!) =====
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("🏦 === SISTEMA BANCÁRIO - CONTROLE DE TRANSAÇÕES FINANCEIRAS ===");
        
        // Dados de exemplo
        criarDadosExemplo();
        
        int opcao;
        do {
            mostrarMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
                
                switch (opcao) {
                    case 1: criarConta(); break;
                    case 2: realizarDeposito(); break;
                    case 3: realizarSaque(); break;
                    case 4: realizarTransferencia(); break;
                    case 5: criarInvestimento(); break;
                    case 6: RepositorioContas.listarContas(); break;
                    case 7: RepositorioContas.mostrarHistorico(); break;
                    case 8: RepositorioContas.listarInvestimentos(); break;
                    case 0: System.out.println("✅ Sistema finalizado com sucesso!"); break;
                    default: System.out.println("❌ Opção inválida! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro na entrada! Digite apenas números.");
                scanner.nextLine(); // Limpar buffer
                opcao = -1; // Continuar no loop
            }
        } while (opcao != 0);
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n🏦 === MENU PRINCIPAL ===");
        System.out.println("1 - Criar Conta");
        System.out.println("2 - Realizar Depósito");
        System.out.println("3 - Realizar Saque");
        System.out.println("4 - Transferir");
        System.out.println("5 - Criar Investimento");
        System.out.println("6 - Listar Contas");
        System.out.println("7 - Histórico de Transações");
        System.out.println("8 - Listar Investimentos");
        System.out.println("0 - Sair");
        System.out.print("➤ Escolha uma opção: ");
    }
    
    private static void criarConta() {
        try {
            System.out.print("Número da conta: ");
            String numero = scanner.nextLine();
            
            System.out.print("Titular: ");
            String titular = scanner.nextLine();
            
            System.out.print("Tipo (1-Corrente, 2-Poupança): ");
            int tipo = scanner.nextInt();
            
            if (tipo == 1) {
                System.out.print("Limite do cheque especial: ");
                double limite = scanner.nextDouble();
                RepositorioContas.adicionarConta(new ContaCorrente(numero, titular, limite));
            } else if (tipo == 2) {
                System.out.print("Taxa de juros (%): ");
                double taxa = scanner.nextDouble();
                RepositorioContas.adicionarConta(new ContaPoupanca(numero, titular, taxa));
            } else {
                System.out.println("❌ Tipo inválido!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao criar conta! Verifique os dados.");
            scanner.nextLine(); // Limpar buffer
        }
    }
    
    private static void realizarDeposito() {
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        
        Conta conta = RepositorioContas.buscarConta(numero);
        if (conta != null) {
            try {
                System.out.print("Valor do depósito: ");
                double valor = scanner.nextDouble();
                if (valor > 0) {
                    conta.depositar(valor);
                    RepositorioContas.adicionarTransacao(
                        new Transacao("DEPÓSITO", valor, "31/08/2025", "Depósito em conta")
                    );
                } else {
                    System.out.println("❌ Valor deve ser positivo!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro no valor! Digite um número válido.");
                scanner.nextLine();
            }
        } else {
            System.out.println("❌ Conta não encontrada!");
        }
    }
    
    private static void realizarSaque() {
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        
        Conta conta = RepositorioContas.buscarConta(numero);
        if (conta != null) {
            try {
                System.out.print("Valor do saque: ");
                double valor = scanner.nextDouble();
                if (valor > 0) {
                    conta.sacar(valor);
                    RepositorioContas.adicionarTransacao(
                        new Transacao("SAQUE", valor, "31/08/2025", "Saque em conta")
                    );
                } else {
                    System.out.println("❌ Valor deve ser positivo!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro no valor! Digite um número válido.");
                scanner.nextLine();
            }
        } else {
            System.out.println("❌ Conta não encontrada!");
        }
    }
    
    private static void realizarTransferencia() {
        System.out.print("Conta origem: ");
        String origem = scanner.nextLine();
        
        System.out.print("Conta destino: ");
        String destino = scanner.nextLine();
        
        Conta contaOrigemObj = RepositorioContas.buscarConta(origem);
        Conta contaDestinoObj = RepositorioContas.buscarConta(destino);
        
        if (contaOrigemObj != null && contaDestinoObj != null) {
            try {
                System.out.print("Valor da transferência: ");
                double valor = scanner.nextDouble();
                if (valor > 0) {
                    contaOrigemObj.transferir(valor, contaDestinoObj);
                    RepositorioContas.adicionarTransacao(
                        new Transacao("TRANSFERÊNCIA", valor, "31/08/2025", 
                                    "De: " + origem + " Para: " + destino)
                    );
                } else {
                    System.out.println("❌ Valor deve ser positivo!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro no valor! Digite um número válido.");
                scanner.nextLine();
            }
        } else {
            System.out.println("❌ Uma ou ambas as contas não foram encontradas!");
        }
    }
    
    private static void criarInvestimento() {
        try {
            System.out.print("Tipo do investimento: ");
            String tipo = scanner.nextLine();
            
            System.out.print("Valor investido: ");
            double valor = scanner.nextDouble();
            
            System.out.print("Rentabilidade (%): ");
            double rentabilidade = scanner.nextDouble();
            
            if (valor > 0 && rentabilidade >= 0) {
                RepositorioContas.adicionarInvestimento(
                    new Investimento(tipo, valor, rentabilidade, "31/08/2025")
                );
            } else {
                System.out.println("❌ Valores devem ser positivos!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao criar investimento! Verifique os dados.");
            scanner.nextLine();
        }
    }
    
    private static void criarDadosExemplo() {
        // Criando contas de exemplo
        RepositorioContas.adicionarConta(new ContaCorrente("001", "João Silva", 500.0));
        RepositorioContas.adicionarConta(new ContaPoupanca("002", "Maria Santos", 0.5));
        
        // Realizando algumas operações de exemplo
        Conta conta1 = RepositorioContas.buscarConta("001");
        Conta conta2 = RepositorioContas.buscarConta("002");
        
        conta1.depositar(1000.0);
        conta2.depositar(2000.0);
        
        // Adicionando transações de exemplo
        RepositorioContas.adicionarTransacao(new Transacao("DEPÓSITO", 1000.0, "30/08/2025", "Depósito inicial"));
        RepositorioContas.adicionarTransacao(new Transacao("DEPÓSITO", 2000.0, "30/08/2025", "Depósito inicial"));
        
        // Investimento de exemplo
        RepositorioContas.adicionarInvestimento(new Investimento("CDB", 5000.0, 12.0, "29/08/2025"));
        
        System.out.println("✅ Sistema inicializado com dados de exemplo!");
        System.out.println("📋 Contas disponíveis: 001 (João) e 002 (Maria)");
    }
}

        RepositorioContas.adicionarInvestimento(new Investimento("CDB", 5000.0, 12.0, "29/08/2025"));

        System.out.println("✅ Sistema inicializado com dados de exemplo!");
        System.out.println("📋 Contas disponíveis: 001 (João) e 002 (Maria)");
    }
}
