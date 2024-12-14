package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.ClientResponseDTO;
import br.com.ecommerce.relogios.dto.OrderItemDTO;
import br.com.ecommerce.relogios.dto.OrderItemResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.OrderItemService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/orderItem")
public class OrderItemResource {

    @Inject
    OrderItemService orderItemService;

    private static final Logger LOG = Logger.getLogger(OrderItemResource.class);

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<OrderItemResponseDTO> orderItems = orderItemService.findAll();
            LOG.info("Sucesso");
            return Response.ok(orderItems).build();
        } catch (NotFoundException e) {
            LOG.warn("OrderItems não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("OrderItems não encontrado").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            OrderItemResponseDTO orderItem = orderItemService.findOrderItemById(id);
            LOG.info("OrderItem encontrado com sucesso");
            return Response.ok(orderItem).build();
        } catch (NotFoundException e) {
            LOG.warn("OrderItem não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("OrderItem não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar OrderItem", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar OrderItem").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(OrderItemDTO orderItemDTO) {
        try {
            LOG.info("Executando o create");
            OrderItemResponseDTO orderItem = orderItemService.create(orderItemDTO);
            LOG.info("OrderItem criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(orderItem).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar OrderItem", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar OrderItem").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, OrderItemDTO orderItemDTO) {
        try {
            LOG.info("Executando o update");
            orderItemService.update(id, orderItemDTO);
            LOG.info("OrderItem atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("OrderItem não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("OrderItem não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar OrderItem", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar OrderItem").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            orderItemService.delete(id);
            LOG.info("OrderItem removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("OrderItem não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("OrderItem não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar OrderItem", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar OrderItem").build();
        }
    }

    @PATCH
    @Path("/addQuantity/{idOrderItem}")
    public Response addQuantity(@PathParam("idOrderItem") Long idOrderItem, Integer quantity) {
        orderItemService.addQuantity(idOrderItem, quantity);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/removeQuantity/{idOrderItem}")
    public Response removeQuantity(@PathParam("idOrderItem") Long idOrderItem, Integer quantity) {
        orderItemService.removeQuantity(idOrderItem, quantity);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
