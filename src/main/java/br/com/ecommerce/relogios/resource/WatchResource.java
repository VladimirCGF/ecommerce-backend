package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.StorageDTO;
import br.com.ecommerce.relogios.dto.WatchDTO;
import br.com.ecommerce.relogios.dto.WatchListResponseDTO;
import br.com.ecommerce.relogios.dto.WatchResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.form.StorageForm;
import br.com.ecommerce.relogios.repository.WatchRepository;
import br.com.ecommerce.relogios.service.FileService;
import br.com.ecommerce.relogios.service.StorageService;
import br.com.ecommerce.relogios.service.WatchService;
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
@Path(value = "/api/watches")
public class WatchResource {

    @Inject
    WatchService watchService;

    @Inject
    WatchRepository watchRepository;

    @Inject
    FileService fileService;

    @Inject
    StorageService storageService;

    private static final Logger LOG = Logger.getLogger(WatchResource.class);

    private static final String UPLOAD_DIR = "/path/to/your/uploads";


    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<WatchListResponseDTO> watches = watchService.findAll();
            LOG.info("Sucesso");
            return Response.ok(watches).build();
        } catch (NotFoundException e) {
            LOG.warn("Watches não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Watches não encontrado").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("view/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            WatchResponseDTO watch = watchService.findWatchById(id);
            LOG.info("Watch encontrado com sucesso");
            return Response.ok(watch).build();
        } catch (NotFoundException e) {
            LOG.warn("Watch não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Watch não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Watch", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Watch").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(WatchDTO watchDTO) {
        try {
            LOG.info("Executando o create");
            WatchResponseDTO watch = watchService.create(watchDTO);
            LOG.info("Watch criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(watch).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Watch", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Watch").build();
        }
    }


    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, WatchDTO watchDTO) {
        try {
            LOG.info("Executando o update");
            watchService.update(id, watchDTO);
            LOG.info("Watch atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Watch não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Watch não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Watch", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Watch").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            watchService.delete(id);
            LOG.info("Watch removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Watch não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Watch não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Watch", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Watch").build();
        }
    }

    @PUT
    @Path("/image/upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response saveImage(@PathParam("id") Long id, @MultipartForm StorageForm form) {
        try {
            LOG.info("Executando o create");
            StorageDTO storageDTO = new StorageDTO(form.getIdWatch(), form.getName());
            watchService.uploadImagePerfil(id, storageDTO, form.imagem);
            LOG.info("Armazenamento criado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (IOException e) {
            LOG.error("Erro ao criar" + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/image/download/{id}/{nameImage}")
    public Response download(@PathParam("id") Long id, @PathParam("nameImage") String nameImage) {
        try {
            InputStream imagemStream = fileService.download(id, nameImage);

            String contentType = Files.probeContentType(Paths.get(nameImage));
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return Response.ok(imagemStream, contentType).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Imagem não encontrada: " + nameImage).build();
        }
    }

//    @GET
//    @Path("/by-order/{orderId}")
//    public Response getWatchesByOrder(@PathParam("orderId") Long orderId) {
//        List<WatchListResponseDTO> watches = watchService.getWatchesByOrderId(orderId);
//        return Response.ok(watches).build();
//    }

    @GET
    @Path("/findByName/{name}")
    public Response getWatchesByName(@PathParam("name") String name) {
        List<WatchResponseDTO> watches = watchService.findByName(name);
        return Response.ok(watches).build();
    }

}
