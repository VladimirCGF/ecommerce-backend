package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.PaymentDTO;
import br.com.ecommerce.relogios.dto.PaymentResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.PaymentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/payment")
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    private static final Logger LOG = Logger.getLogger(PaymentResource.class);

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<PaymentResponseDTO> payments = paymentService.findAll();
            LOG.info("Sucesso");
            return Response.ok(payments).build();
        } catch (NotFoundException e) {
            LOG.warn("Payments não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Payments não encontrado").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            PaymentResponseDTO payment = paymentService.findPaymentById(id);
            LOG.info("Payment encontrado com sucesso");
            return Response.ok(payment).build();
        } catch (NotFoundException e) {
            LOG.warn("Payment não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Payment não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Payment", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Payment").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(PaymentDTO paymentDTO) {
        try {
            LOG.info("Executando o create");
            PaymentResponseDTO payment = paymentService.create(paymentDTO);
            LOG.info("Payment criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(payment).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Payment", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Payment").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PaymentDTO paymentDTO) {
        try {
            LOG.info("Executando o update");
            paymentService.update(id, paymentDTO);
            LOG.info("Payment atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Payment não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Payment não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Payment", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Payment ao atualizar Stock").build();
        }
    }

//    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            paymentService.delete(id);
            LOG.info("Payment removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Payment não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Payment não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Payment", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Payment").build();
        }
    }
}
