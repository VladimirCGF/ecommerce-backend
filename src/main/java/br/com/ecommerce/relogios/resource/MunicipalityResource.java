package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.MunicipalityDTO;
import br.com.ecommerce.relogios.dto.MunicipalityResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.MunicipalityService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.security.RolesAllowed;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/municipality")
public class MunicipalityResource {

    @Inject
    MunicipalityService municipalityService;

    private static final Logger LOG = Logger.getLogger(MunicipalityResource.class);

    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<MunicipalityResponseDTO> municipalities = municipalityService.findAll();
            LOG.info("Sucesso");
            return Response.ok(municipalities).build();
        } catch (NotFoundException e) {
            LOG.warn("Municipalities não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Municipalities não encontrado").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            MunicipalityResponseDTO municipality = municipalityService.findMunicipalityById(id);
            LOG.info("Municipality encontrado com sucesso");
            return Response.ok(municipality).build();
        } catch (NotFoundException e) {
            LOG.warn("Municipality não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Municipality não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Municipality", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Municipality").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(MunicipalityDTO municipalityDTO) {
        try {
            LOG.info("Executando o create");
            MunicipalityResponseDTO municipality = municipalityService.create(municipalityDTO);
            LOG.info("Municipality criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(municipality).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Municipality", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Municipality").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, MunicipalityDTO municipalityDTO) {
        try {
            LOG.info("Executando o update");
            municipalityService.update(id, municipalityDTO);
            LOG.info("Municipality atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Municipality não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Municipality não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Municipality", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Municipality").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            municipalityService.delete(id);
            LOG.info("Municipality removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Municipality não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Municipality não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Municipality", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Municipality").build();
        }
    }

    @GET
    @Path("/state/{idState}")
    public List<MunicipalityResponseDTO> getMunicipalitiesByState(@PathParam("idState") Long idState) {
        return municipalityService.getMunicipalitiesByState(idState);
    }

    @GET
    @Path("/valid")
    public Boolean valid(@QueryParam("id") Long id, @QueryParam("name") String name, @QueryParam("idState") Long idState) {
        LOG.info("Executando o checkNameUnique");
        return municipalityService.checkNameUnique(id, name, idState);
    }
}
