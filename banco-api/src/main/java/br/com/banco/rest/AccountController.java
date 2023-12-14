package br.com.banco.rest;


import br.com.banco.persistence.dto.AccountDetailsDto;
import br.com.banco.persistence.dto.AccountDto;
import br.com.banco.services.AccountService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;


@Path("/api/v1/accounts")
public class AccountController {

    @Inject
    AccountService accountService;

    @POST
    @Path("/criarConta")
    public Response criarConta(AccountDto accountDto) {
        try {
            accountService.criarConta(accountDto);
            return Response.status(Response.Status.CREATED).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar conta").build();
        }
    }

    @POST
    @Path("/depositar")
    public Response depositar(@FormParam("numeroConta") String numeroConta, @FormParam("valor") BigDecimal valor) {
        try {
            if (valor == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("O valor de depósito não pode ser nulo.").build();
            }

            accountService.depositar(numeroConta, valor);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Conta não encontrada").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao processar depósito").build();
        }
    }

    @POST
    @Path("/sacar")
    public Response sacar(@FormParam("numeroConta") String numeroConta, @FormParam("valor") BigDecimal valor) {
        try {
            if (valor == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("O valor de saque não pode ser nulo.").build();
            }

            accountService.sacar(numeroConta, valor);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Conta não encontrada").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao processar saque").build();
        }
    }

    @GET
    @Path("/listarContas")
    public Response listarContas() {
        try {
            List<AccountDto> contas = accountService.listarContas();
            return Response.status(Response.Status.OK).entity(contas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar contas").build();
        }
    }

    @GET
    @Path("/detalhesConta/{numeroConta}")
    public Response getAccountDetails(@PathParam("numeroConta") String numeroConta) {
        try {
            AccountDetailsDto accountDetails = accountService.getAccountDetails(numeroConta);
            return Response.status(Response.Status.OK).entity(accountDetails).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao obter detalhes da conta").build();
        }
    }

}


