package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.StorageDTO;
import br.com.ecommerce.relogios.dto.StorageResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.form.StorageForm;
import br.com.ecommerce.relogios.service.FileService;
import br.com.ecommerce.relogios.service.StorageService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/storage")
public class StorageResource {

    @Inject
    StorageService storageService;

    @Inject
    FileService fileService;

    private static final Logger LOG = Logger.getLogger(StorageResource.class);

    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    @Path("/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@MultipartForm StorageForm form) {
        try {
            LOG.info("Executando o create");
            StorageDTO storageDTO = new StorageDTO(form.getIdWatch(), form.getName());
            LOG.info("idWatch: " + form.getIdWatch());
            LOG.info("nameImage: " + form.getName());
            LOG.info("imagem: " + form.getImagem());
            StorageResponseDTO storageResponseDTO = storageService.create(storageDTO, form.imagem);
            LOG.info("Armazenamento criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(storageResponseDTO).build();
        } catch (IOException e) {
            LOG.error("Erro ao criar" + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar: " + e.getMessage()).build();
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
            LOG.warn("Armazenamentos não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Armazenamentos não encontrado").build();
        }
    }

    @GET
    @Path("/findAllByWatchId/{id}")
    public Response findAllByWatchId(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            List<StorageResponseDTO> storages = storageService.findAllByWatchId(id);
            LOG.info("Armazenamento encontrado com sucesso");
            return Response.ok(storages).build();
        } catch (NotFoundException e) {
            LOG.warn("Armazenamento não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Armazenamento não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Armazenamento", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Armazenamento").build();
        }
    }


    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            StorageResponseDTO storage = storageService.findById(id);
            LOG.info("Armazenamento encontrado com sucesso");
            return Response.ok(storage).build();
        } catch (NotFoundException e) {
            LOG.warn("Armazenamento não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Armazenamento não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Armazenamento", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Armazenamento").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("{id}/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(@PathParam("id") Long id, @MultipartForm StorageForm form) {
        try {
            LOG.info("Executando o update");
            StorageDTO storageDTO = new StorageDTO(form.getIdWatch(), form.getName());
            storageService.update(id, storageDTO, form.imagem);
            LOG.info("Armazenamento atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Armazenamento não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Armazenamento não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Armazenamento", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Armazenamento").build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            storageService.delete(id);
            LOG.info("Armazenamento removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Armazenamento não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Armazenamento não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Armazenamento", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Armazenamento").build();
        }
    }

    @GET
    @Path("/image/download/{id}/{name}")
    public Response download(@PathParam("id") Long id, @PathParam("name") String name) {
        try {
            InputStream imagemStream = fileService.download(id, name);
            String contentType = Files.probeContentType(Paths.get(name));

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            Response.ResponseBuilder response = Response.ok(imagemStream);
            response.header("Content-Disposition", "attachment;filename=" + name);
            response.header("Content-Type", contentType);

            return response.build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Imagem não encontrada: " + name).build();
        }
    }

}
