package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.AddressDTO;
import br.com.ecommerce.relogios.dto.AddressResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.AddressService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/address")
public class AddressResource {

    @Inject
    AddressService addressService;

    private static final Logger LOG = Logger.getLogger(AddressResource.class);

//    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<AddressResponseDTO> address = addressService.findAll();
            LOG.info("Sucesso");
            return Response.ok(address).build();
        } catch (NotFoundException e) {
            LOG.warn("Address não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Address não encontrado").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            AddressResponseDTO address = addressService.findAddressById(id);
            LOG.info("Address encontrado com sucesso");
            return Response.ok(address).build();
        } catch (NotFoundException e) {
            LOG.warn("Address não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Address não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Address", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Address").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(AddressDTO addressDTO) {
        try {
            LOG.info("Executando o create");
            AddressResponseDTO address = addressService.create(addressDTO);
            LOG.info("Address criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(address).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Address", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Address").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AddressDTO addressDTO) {
        try {
            LOG.info("Executando o update");
            addressService.update(id, addressDTO);
            LOG.info("Address atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Address não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Address não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Address", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Address").build();
        }
    }

    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            addressService.delete(id);
            LOG.info("Address removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Address não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Address não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Address", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Address").build();
        }
    }

    @GET
    @Path("/myList/{id}")
    public Response findAddressByIdClient(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findAddressByIdClient");
            List<AddressResponseDTO> address = addressService.findAddressByIdClient(id);
            LOG.info("Sucesso");
            return Response.ok(address).build();
        } catch (NotFoundException e) {
            LOG.warn("Address não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Address não encontrado").build();
        }
    }
}
