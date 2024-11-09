package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.WatchDTO;
import br.com.ecommerce.relogios.dto.WatchResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.form.FileUploadForm;
import br.com.ecommerce.relogios.model.Watch;
import br.com.ecommerce.relogios.repository.WatchRepository;
import br.com.ecommerce.relogios.service.WatchService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/watches")
public class WatchResource {

    @Inject
    WatchService watchService;

    @Inject
    WatchRepository watchRepository;

    private static final Logger LOG = Logger.getLogger(WatchResource.class);

//    private static final String UPLOAD_DIR = "uploads";

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


    @POST
    @Path("/{id}/upload-imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response uploadImagem(@PathParam("id") Long id, @MultipartForm FileUploadForm form) {

        // Verifica se o relógio existe
        Watch watch = watchRepository.findById(id);
        if (watch == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Watch não encontrado").build();
        }

        // Cria o diretório de upload se não existir
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Pega o nome do arquivo da solicitação
        String fileName = form.getFileData().toString();  // Obtém automaticamente o nome do arquivo enviado
        if (fileName == null || fileName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Nome do arquivo não fornecido").build();
        }

        // Define o caminho completo para salvar a imagem
        String imageUrl = UPLOAD_DIR + "/" + id + "-" + fileName;  // O nome do arquivo será automaticamente obtido

        try (InputStream in = form.getFileData();
             OutputStream out = new FileOutputStream(imageUrl)) {

            byte[] buffer = new byte[1024]; // Buffer para transferência de dados
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            // Adiciona a nova URL à lista de URLs de imagem
            List<String> imageUrls = watch.getImageUrl();
            if (imageUrls == null) {
                imageUrls = new ArrayList<>();
                watch.setImageUrl(imageUrls);
            }
            imageUrls.add(imageUrl);  // Adiciona a URL da imagem ao objeto watch

            // Persiste a alteração no banco de dados
            watchRepository.persist(watch);

            return Response.ok("Imagem enviada com sucesso").build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao salvar a imagem: " + e.getMessage()).build();
        }
    }


    public static class UploadResponse {
        public String fileName;

        public UploadResponse(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }


    @GET
    @Path("/{id}/download-imagem")
    @Produces({MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON})
    public Response downloadImagem(@PathParam("id") Long id, @QueryParam("imageIndex") int imageIndex) {
        // Verifica se o Watch existe
        Watch watch = watchRepository.findById(id);
        if (watch == null || watch.getImageUrl() == null || watch.getImageUrl().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Watch ou imagens não encontradas").build();
        }

        // Verifica se o índice é válido
        List<String> imageUrls = watch.getImageUrl();
        if (imageIndex < 0 || imageIndex >= imageUrls.size()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Índice de imagem inválido").build();
        }

        // Obtém o caminho completo da imagem pelo índice
        String imageUrl = imageUrls.get(imageIndex);
        File file = new File(imageUrl);
        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Imagem não encontrada").build();
        }

        // Cria uma resposta para enviar o arquivo
        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        return response.build();
    }

    @GET
    @Path("image/{fileName}")
    @Produces({"image/png", "image/jpeg", "image/jpg", "image/gif"})
    public Response getImage( @PathParam("fileName") String fileName) {
        File imageFile = new File(UPLOAD_DIR,fileName);
        if (!imageFile.exists()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Imagem não encontrada").build();
        }
        return Response.ok(imageFile).build();
    }


}
