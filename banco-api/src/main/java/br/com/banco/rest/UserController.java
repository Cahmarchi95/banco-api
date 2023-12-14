package br.com.banco.rest;

import br.com.banco.persistence.dto.UserDto;
import br.com.banco.services.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


@Path("/api/v1/users")
public class UserController {


    @Inject
    UserService userService;


    @POST
    public Response createUser(@Valid UserDto userDto) {
        userService.createUser(userDto);
        return Response.status(Response.Status.CREATED).entity("Usuário cadastrado com sucesso!").build();
    }


}
