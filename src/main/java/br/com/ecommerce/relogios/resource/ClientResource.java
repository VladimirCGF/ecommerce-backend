package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.*;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.ClientService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/client")
public class ClientResource {

    @Inject
    ClientService clientService;

    private static final Logger LOG = Logger.getLogger(ClientResource.class);

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<ClientResponseDTO> client = clientService.findAll();
            LOG.info("Sucesso");
            return Response.ok(client).build();
        } catch (NotFoundException e) {
            LOG.warn("Clientes não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Clientes não encontrado").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            ClientResponseDTO client = clientService.findClientById(id);
            LOG.info("Cliente encontrado com sucesso");
            return Response.ok(client).build();
        } catch (NotFoundException e) {
            LOG.warn("Cliente não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Cliente", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Cliente").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(ClientDTO clientDTO) {
        try {
            LOG.info("Executando o create");
            ClientResponseDTO client = clientService.create(clientDTO);
            LOG.info("Cliente criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(client).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Cliente", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Cliente").build();
        }
    }


//    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ClientDTO clientDTO) {
        try {
            LOG.info("Executando o update");
            clientService.update(id, clientDTO);
            LOG.info("Cliente atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Cliente não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Cliente", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Cliente").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            clientService.delete(id);
            LOG.info("Cliente removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Cliente não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Cliente", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Cliente").build();
        }
    }

    @GET
    @Path("/valid")
    public Boolean valid(@QueryParam("id") Long id, @QueryParam("email") String email) {
        LOG.info("Executando o checkEmailUnique");
        return clientService.checkEmailUnique(id, email);
    }

    @POST
    @Path("/createClient")
    public Response createClient(ClientDTO clientDTO) {
        try {
            LOG.info("Executando o createClient");
            ClientResponseDTO client = clientService.createClient(clientDTO);
            LOG.info("Cliente criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(client).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Cliente", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Cliente").build();
        }
    }

    @RolesAllowed("Cliente")
    @POST
    @Path("/addAddress")
    public Response addAddress(AddressDTO addressDTO) {
        try {
            LOG.info("Executando o addAddress");
            clientService.addAddress(addressDTO);
            LOG.info("Address adicionado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Address não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Address não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao adicionar Address", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao adicionar Address").build();
        }
    }

    @RolesAllowed("Cliente")
    @PATCH
    @Path("/removerAddress/{id}")
    public Response removerAddress(@Valid @PathParam("id") Long id) {
        try {
            LOG.info("Executando o removerAddress");
            clientService.removeAddress(id);
            LOG.info("Address removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Address não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Address não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao remover Address", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover Address").build();
        }
    }

    @RolesAllowed("Cliente")
    @POST
    @Path("/addItem")
    public Response addItem(AddItemOrderDTO addItemOrderDTO) {
        try {
            LOG.info("Executando o addItem");
            clientService.addItem(addItemOrderDTO);
            LOG.info("Item adicionado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Item não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Item não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao adicionar Item", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao adicionar Item").build();
        }
    }

    @RolesAllowed("Cliente")
    @PUT
    @Path("/removeItem")
    public Response removeItem(Long idOrderItem) {
        try {
            LOG.info("Executando o addItem");
            clientService.removerItem(idOrderItem);
            LOG.info("Item removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Item não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Item não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao remover Item", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover Item").build();
        }
    }


    @RolesAllowed("Cliente")
    @POST
    @Path("/checkout")
    public Response checkout(OrdersDTO ordersDTO) {
        try {
            LOG.info("Executando o checkout");
            clientService.checkout(ordersDTO);
            LOG.info("Checkout criado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao fazer Checkout", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao fazer Checkout").build();
        }
    }

    @RolesAllowed("Cliente")
    @POST
    @Path("/payment")
    public Response payment(PaymentDTO paymentDTO) {
        try {
            LOG.info("Executando o payment");
            clientService.payment(paymentDTO);
            LOG.info("Payment realizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao efetuar Payment", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao efetuar Payment").build();
        }
    }

    @RolesAllowed("Cliente")
    @GET
    @Path("/myListPayment")
    public Response findMyListPayment() {
        try {
            LOG.info("Executando o findMyListPayment");
            List<PaymentResponseDTO> list = clientService.findMyPayments();
            LOG.info("Sucesso");
            return Response.ok(list).build();
        } catch (NotFoundException e) {
            LOG.warn("Payment não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Payment não encontrado").build();
        }
    }

    @RolesAllowed("Cliente")
    @GET
    @Path("/myListAddress")
    public Response findMyListAddress() {
        try {
            LOG.info("Executando o findMyListAddress");
            List<AddressResponseDTO> address = clientService.findMyListAddress();
            LOG.info("Sucesso");
            return Response.ok(address).build();
        } catch (NotFoundException e) {
            LOG.warn("Address não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Address não encontrado").build();
        }
    }

    @RolesAllowed("Cliente")
    @GET
    @Path("/myListOrders")
    public Response findMyListOrders() {
        try {
            LOG.info("Executando o findMyListOrders");
            List<OrdersResponseDTO> orders = clientService.findMyListOrders();
            LOG.info("Sucesso");
            return Response.ok(orders).build();
        } catch (NotFoundException e) {
            LOG.warn("Orders não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Orders não encontrado").build();
        }
    }

    @RolesAllowed("Cliente")
    @GET
    @Path("/me")
    public Response getLoggedUser() {
        try {
            LOG.info("Executando o getLoggedUser");
            ClientResponseDTO user = ClientResponseDTO.valueOf(clientService.getLoggedClient());
            LOG.info("User encontrado com sucesso");
            return Response.ok(user).build();
        } catch (NotFoundException e) {
            LOG.warn("Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Usuário", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Usuário").build();
        }
    }

}
