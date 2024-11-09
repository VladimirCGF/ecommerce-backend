package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.StateDTO;
import br.com.ecommerce.relogios.dto.StateResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.StateService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/states")
public class StateResource {

    @Inject
    StateService stateService;

    private static final Logger LOG = Logger.getLogger(StateResource.class);

    //    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<StateResponseDTO> state = stateService.findAll();
            LOG.info("Sucesso");
            return Response.ok(state).build();
        } catch (NotFoundException e) {
            LOG.warn("State não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("State não encontrado").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            StateResponseDTO state = stateService.findStateById(id);
            LOG.info("State encontrado com sucesso");
            return Response.ok(state).build();
        } catch (NotFoundException e) {
            LOG.warn("State não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("State não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar State", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar State").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(StateDTO stateDTO) {
        try {
            LOG.info("Executando o create");
            StateResponseDTO state = stateService.create(stateDTO);
            LOG.info("State criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(state).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar State", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar State").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, StateDTO stateDTO) {
        try {
            LOG.info("Executando o update");
            stateService.update(id, stateDTO);
            LOG.info("State atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("State não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("State não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar State", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar State").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            stateService.delete(id);
            LOG.info("State removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("State não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("State não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar State", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar State").build();
        }
    }

    @GET
    @Path("/validName")
    public Boolean validName(@QueryParam("id") Long id, @QueryParam("name") String name) {
        LOG.info("Executando o checkNameUnique");
        return stateService.checkNameUnique(id, name);
    }

    @GET
    @Path("/validAbbreviations")
    public Boolean validAbbreviations(@QueryParam("id") Long id, @QueryParam("abbreviations") String abbreviations) {
        LOG.info("Executando o checkAbbreviationsUnique");
        return stateService.checkAbbreviationsUnique(id, abbreviations);
    }
}
