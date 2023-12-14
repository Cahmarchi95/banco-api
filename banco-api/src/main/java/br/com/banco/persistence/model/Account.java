package br.com.banco.persistence.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Random;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;


    @Column(name = "saldoAtual")
    private BigDecimal saldoAtual;

    @Column(name = "numeroConta", unique = true)
    private String numeroConta;

    @Column(name = "nomeUsuario")
    private String nomeUsuario;

    @Column(name = "account_type")
    private int accountType;

    public Account() {
        this.numeroConta = gerarNumeroConta();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(BigDecimal saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }


    private String gerarNumeroConta() {
        Random random = new Random();
        int numeroAleatorio = 10000 + random.nextInt(90000);
        int digitoAleatorio = random.nextInt(10);
        return String.format("%05d-%d", numeroAleatorio, digitoAleatorio);
    }

    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }

        BigDecimal novoValor = valor;
        if (accountType == 2) {
            novoValor = novoValor.add(BigDecimal.valueOf(0.5));
        }

        saldoAtual = saldoAtual.add(novoValor);
    }

    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque inválido.");
        }

        if (valor.compareTo(saldoAtual) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente para o saque.");
        }

        saldoAtual = saldoAtual.subtract(valor);
    }
}
