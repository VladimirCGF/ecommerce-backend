package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.StorageDTO;
import br.com.ecommerce.relogios.dto.StorageResponseDTO;
import br.com.ecommerce.relogios.dto.UserDTO;
import br.com.ecommerce.relogios.dto.UserResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.form.StorageForm;
import br.com.ecommerce.relogios.service.StorageService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.IOException;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/storage")
public class StorageResource {

    @Inject
    StorageService storageService;

    private static final Logger LOG = Logger.getLogger(StorageResource.class);

    @POST
    @Path("/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@MultipartForm StorageForm form) {
        try {
            LOG.info("Executando o create");
            StorageDTO storageDTO = new StorageDTO(form.getIdWatch(), form.getName());
            StorageResponseDTO storageResponseDTO = storageService.create(storageDTO, form.imagem);
            LOG.info("Usuário criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(storageResponseDTO).build();
        } catch (RuntimeException e) {
            LOG.error("Erro ao criar" +  e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Usuário").build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<StorageResponseDTO> storages = storageService.findAll();
            LOG.info("Sucesso");
            return Response.ok(storages).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuários não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuários não encontrado").build();
        }
    }

    @GET
    @Path("/findAllByWatchId/{id}")
    public Response findAllByWatchId(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            List<StorageResponseDTO> storages = storageService.findAllByWatchId(id);
            LOG.info("Usuário encontrado com sucesso");
            return Response.ok(storages).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Usuário", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Usuário").build();
        }
    }


    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            StorageResponseDTO storage = storageService.findById(id);
            LOG.info("Usuário encontrado com sucesso");
            return Response.ok(storage).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Usuário", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Usuário").build();
        }
    }

    @PUT
    @Path("{id}/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(@PathParam("id") Long id, @MultipartForm StorageForm form) {
        try {
            LOG.info("Executando o update");
            StorageDTO storageDTO = new StorageDTO(form.getIdWatch(), form.getName());
            storageService.update(id, storageDTO, form.imagem);
            LOG.info("Usuário atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Usuário", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Usuário").build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            storageService.delete(id);
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

}
