package br.com.banco.services;

import br.com.banco.persistence.dao.AccountDao;
import br.com.banco.persistence.dto.AccountDetailsDto;
import br.com.banco.persistence.dto.AccountDto;
import br.com.banco.persistence.model.Account;
import br.com.banco.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;



@ApplicationScoped
public class AccountService {

    private final AccountDao accountDao;
    private final UserService userService;

    @Inject
    public AccountService(AccountDao accountDao, UserService userService) {
        this.accountDao = accountDao;
        this.userService = userService;
    }

    @Transactional
    public void criarConta(AccountDto accountDto) {
        User user = userService.findUserById(accountDto.getUserId());

        if (user == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        Account account = new Account();
        account.setUser(user);
        account.setAccountType(accountDto.getAccountType());
        account.setSaldoAtual(BigDecimal.ZERO);
        account.setNomeUsuario(user.getNome());
        this.accountDao.create(account);
    }

    public List<AccountDto> listarContas() {
        List<Account> accounts = accountDao.listarContas();

        return accounts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AccountDto convertToDTO(Account account) {
        AccountDto dto = new AccountDto();
        dto.setNumeroConta(account.getNumeroConta());
        dto.setAccountType(account.getAccountType());
        dto.setSaldoAtual(account.getSaldoAtual());

        User user = account.getUser();
        if (user != null) {
            dto.setUserId(user.getId());
            dto.setNomeUsuario(user.getNome());
        }

        return dto;
    }

    @Transactional
    public void depositar(@FormParam("numeroConta") String numeroConta, @FormParam("valor") BigDecimal valor) {
        Account account = accountDao.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        validarValorPositivo(valor);

        account.depositar(valor);

        accountDao.save(account);
    }

    @Transactional
    public void sacar(@FormParam("numeroConta") String numeroConta, @FormParam("valor") BigDecimal valor) {
        Account account = accountDao.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        validarValorPositivo(valor);

        account.sacar(valor);

        accountDao.save(account);
    }

    private void validarValorPositivo(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("O valor deve ser maior que zero.");
        }
    }

    @Transactional
    public AccountDetailsDto getAccountDetails(String numeroConta) {
        Account account = accountDao.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        User user = account.getUser();
        if (user == null) {
            throw new NotFoundException("Usuário não encontrado para esta conta");
        }

        AccountDetailsDto detailsDto = new AccountDetailsDto();
        detailsDto.setNumeroConta(account.getNumeroConta());
        detailsDto.setAccountType(account.getAccountType());
        detailsDto.setSaldoAtual(account.getSaldoAtual());
        detailsDto.setNomeUsuario(user.getNome());
        detailsDto.setIdade(user.getIdade());
        detailsDto.setTelefone(user.getTelefone());
        detailsDto.setEndereco(user.getEndereco());

        return detailsDto;
    }

}




