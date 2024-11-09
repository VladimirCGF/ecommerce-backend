package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.StockDTO;
import br.com.ecommerce.relogios.dto.StockResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.StockService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/stock")
public class StockResource {

    @Inject
    StockService stockService;

    private static final Logger LOG = Logger.getLogger(StockResource.class);

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<StockResponseDTO> stock = stockService.findAll();
            LOG.info("Sucesso");
            return Response.ok(stock).build();
        } catch (NotFoundException e) {
            LOG.warn("Stock não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Stock não encontrado").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            StockResponseDTO stock = stockService.findStockById(id);
            LOG.info("Stock encontrado com sucesso");
            return Response.ok(stock).build();
        } catch (NotFoundException e) {
            LOG.warn("Stock não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Stock não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Stock", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Stock").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(StockDTO stockDTO) {
        try {
            LOG.info("Executando o create");
            StockResponseDTO stock = stockService.create(stockDTO);
            LOG.info("Stock criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(stock).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Stock", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Stock").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, StockDTO stockDTO) {
        try {
            LOG.info("Executando o update");
            stockService.update(id, stockDTO);
            LOG.info("Stock atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Stock não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Stock não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Stock", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Stock").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            stockService.delete(id);
            LOG.info("Stock removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Stock não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Stock não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Stock", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Stock").build();
        }
    }
}
