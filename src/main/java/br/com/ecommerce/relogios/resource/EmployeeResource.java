package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.EmployeeDTO;
import br.com.ecommerce.relogios.dto.EmployeeResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/employee")
public class EmployeeResource {

    @Inject
    EmployeeService employeeService;

    private static final Logger LOG = Logger.getLogger(EmployeeResource.class);

    //    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<EmployeeResponseDTO> employees = employeeService.findAll();
            LOG.info("Sucesso");
            return Response.ok(employees).build();
        } catch (NotFoundException e) {
            LOG.warn("Funcionários não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Funcionários não encontrado").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            EmployeeResponseDTO employee = employeeService.findEmployeeById(id);
            LOG.info("Funcionário encontrado com sucesso");
            return Response.ok(employee).build();
        } catch (NotFoundException e) {
            LOG.warn("Funcionário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Funcionário", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Funcionário").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(EmployeeDTO employeeDTO) {
        try {
            LOG.info("Executando o create");
            EmployeeResponseDTO employee = employeeService.create(employeeDTO);
            LOG.info("Funcionário criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(employee).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Funcionário", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Funcionário").build();
        }
    }


    //    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EmployeeDTO employeeDTO) {
        try {
            LOG.info("Executando o update");
            employeeService.update(id, employeeDTO);
            LOG.info("Funcionário atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Funcionário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Funcionário", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Funcionário").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            employeeService.delete(id);
            LOG.info("Funcionário removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Funcionário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Funcionário", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Funcionário").build();
        }
    }

    @GET
    @Path("/valid")
    public Boolean valid(@QueryParam("id") Long id, @QueryParam("email") String email) {
        LOG.info("Executando o checkEmailUnique");
        return employeeService.checkEmailUnique(id, email);
    }
}
