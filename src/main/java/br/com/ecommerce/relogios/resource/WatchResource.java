package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.WatchDTO;
import br.com.ecommerce.relogios.dto.WatchResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.form.WatchImageForm;
import br.com.ecommerce.relogios.model.Watch;
import br.com.ecommerce.relogios.repository.WatchRepository;
import br.com.ecommerce.relogios.service.FileService;
import br.com.ecommerce.relogios.service.WatchService;
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

    private static final Logger LOG = Logger.getLogger(WatchResource.class);

    private static final String UPLOAD_DIR = "/path/to/your/uploads";

    //    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<WatchResponseDTO> watches = watchService.findAll();
            LOG.info("Sucesso");
            return Response.ok(watches).build();
        } catch (NotFoundException e) {
            LOG.warn("Watches não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Watches não encontrado").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
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

    //        @RolesAllowed({"Admin", "Funcionario"})
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


    //    @RolesAllowed({"Admin", "Funcionario"})
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

    //    @RolesAllowed({"Admin", "Funcionario"})
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

    @PATCH
    @Path("/image/upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response saveImage(@PathParam("id") Long id, @MultipartForm WatchImageForm form) {
        try {
            fileService.save(id, form.getNameImage(), form.getImagem());
            return Response.noContent().build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao salvar a imagem\", \"error\": \"" + e.getMessage() + "\"}")
                    .build();
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

            Response.ResponseBuilder response = Response.ok(imagemStream);
            response.header("Content-Disposition", "attachment;filename=" + nameImage);
            response.header("Content-Type", contentType);

            return response.build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Imagem não encontrada: " + nameImage).build();
        }
    }

//    @GET
//    @Path("/images/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getImagesByWatchId(@PathParam("id") Long id) {
//        Watch watch = watchRepository.findById(id);
//        if (watch == null) {
//            throw new NotFoundException("Watch não encontrado com o ID: " + id);
//        }
//
//        List<String> imageUrls = watch.getImageUrls();
//        if (imageUrls == null || imageUrls.isEmpty()) {
//            return Response.status(Response.Status.NO_CONTENT)
//                    .entity("Nenhuma imagem encontrada para este relógio")
//                    .build();
//        }
//
//        return Response.ok(imageUrls).build();
//    }
//
//    @PUT
//    @Path("atualizarImageUrl/{id}")
//    public Response saveImageNamesFromDirectory(@PathParam("id") Long id) throws IOException {
//        watchService.saveImageNamesFromDirectory(id);
//        return Response.status(Response.Status.NO_CONTENT).build();
//    }
//
//    @GET
//    @Path("/imageUrlsById/{id}")
//    public Response getImageUrlsById(@PathParam("id") Long id) {
//        List<String> imageUrls = watchService.getImageUrlsById(id);
//        return Response.ok(imageUrls).build();
//    }

}
