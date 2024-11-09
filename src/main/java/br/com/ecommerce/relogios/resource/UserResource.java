package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.UserDTO;
import br.com.ecommerce.relogios.dto.UserResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/users")
public class UserResource {

    @Inject
    UserService userService;

    private static final Logger LOG = Logger.getLogger(UserResource.class);

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<UserResponseDTO> users = userService.findAll();
            LOG.info("Sucesso");
            return Response.ok(users).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuários não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuários não encontrado").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            UserResponseDTO user = userService.findUserById(id);
            LOG.info("Usuário encontrado com sucesso");
            return Response.ok(user).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Usuário", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Usuário").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(UserDTO userDTO) {
        try {
            LOG.info("Executando o create");
            UserResponseDTO user = userService.create(userDTO);
            LOG.info("Usuário criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(user).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Usuário", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Usuário").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, UserDTO userDTO) {
        try {
            LOG.info("Executando o update");
            userService.update(id, userDTO);
            LOG.info("Usuário atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Usuário", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Usuário").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            userService.delete(id);
            LOG.info("Usuário removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Usuário", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Usuário").build();
        }
    }

    @RolesAllowed({"Admin","Funcionario", "Cliente"})
    @GET
    @Path("/me")
    public Response getLoggedUser() {
        try {
            LOG.info("Executando o getLoggedUser");
            UserResponseDTO user = UserResponseDTO.valueOf(userService.getLoggedUser());
            LOG.info("User encontrado com sucesso");
            return Response.ok(user).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Usuário", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Usuário").build();
        }
    }

}
