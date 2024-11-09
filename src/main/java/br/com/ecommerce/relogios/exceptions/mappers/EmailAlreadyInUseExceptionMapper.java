package br.com.ecommerce.relogios.exceptions.mappers;


import br.com.ecommerce.relogios.exceptions.EmailAlreadyInUseException;
import br.com.ecommerce.relogios.exceptions.Problem;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EmailAlreadyInUseExceptionMapper implements ExceptionMapper<EmailAlreadyInUseException> {
    @Override
    public Response toResponse(EmailAlreadyInUseException exception) {
        Problem problem = new Problem(exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
    }
}
