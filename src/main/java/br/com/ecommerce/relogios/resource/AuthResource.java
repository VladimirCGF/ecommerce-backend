package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.AuthUserDTO;
import br.com.ecommerce.relogios.dto.UserResponseDTO;
import br.com.ecommerce.relogios.service.ClientService;
import br.com.ecommerce.relogios.service.HashService;
import br.com.ecommerce.relogios.service.JwtService;
import br.com.ecommerce.relogios.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

    @Inject
    UserService userService;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @POST
    public Response login(AuthUserDTO authUserDTO) {

        String hash = hashService.getHashPassword(authUserDTO.password());

        UserResponseDTO user = userService.login(authUserDTO.email(), hash);

        return Response.ok(user).header("Authorization", jwtService.generateJwt(user)).build();
    }

}
