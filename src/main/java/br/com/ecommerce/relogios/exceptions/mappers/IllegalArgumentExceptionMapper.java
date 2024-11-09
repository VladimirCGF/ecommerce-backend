package br.com.ecommerce.relogios.exceptions.mappers;



import br.com.ecommerce.relogios.exceptions.Problem;
import br.com.ecommerce.relogios.exceptions.ProblemObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.Collections;


@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        Problem problem = new Problem(e);
        problem.setMessages(new ArrayList<>(Collections.singletonList(new ProblemObject(lastFieldName(e), e.getMessage()))));
        return Response.status(Response.Status.BAD_REQUEST).entity(problem).type(MediaType.APPLICATION_JSON).build();
    }

    private String lastFieldName(IllegalArgumentException e) {
        if (e.getMessage() == null) {
            return "";
        }
        String[] words = e.getMessage().split(" ");
        if (words.length >= 2) {
            return words[1].trim();
        }
        return "Campo desconhecido";
    }

}
