package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.AddressResponseDTO;
import br.com.ecommerce.relogios.dto.OrdersDTO;
import br.com.ecommerce.relogios.dto.OrdersResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.OrdersService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/orders")
public class OrdersResource {

    @Inject
    OrdersService ordersService;

    private static final Logger LOG = Logger.getLogger(OrdersResource.class);

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<OrdersResponseDTO> orders = ordersService.findAll();
            LOG.info("Sucesso");
            return Response.ok(orders).build();
        } catch (NotFoundException e) {
            LOG.warn("Orders não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Orders não encontrado").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            OrdersResponseDTO orders = ordersService.findOrdersById(id);
            LOG.info("Orders encontrado com sucesso");
            return Response.ok(orders).build();
        } catch (NotFoundException e) {
            LOG.warn("Orders não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Orders não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Orders", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Orders ao buscar Address").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(OrdersDTO ordersDTO) {
        try {
            LOG.info("Executando o create");
            OrdersResponseDTO orders = ordersService.create(ordersDTO);
            LOG.info("Orders criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(orders).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Orders", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Orders").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, OrdersDTO ordersDTO) {
        try {
            LOG.info("Executando o update");
            ordersService.update(id, ordersDTO);
            LOG.info("Orders atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Orders não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Orders não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Orders", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Orders").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            ordersService.delete(id);
            LOG.info("Orders removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Orders não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Orders não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Orders", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Orders").build();
        }
    }
}
