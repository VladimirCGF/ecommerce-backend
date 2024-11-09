package br.com.ecommerce.relogios.resource;

import br.com.ecommerce.relogios.dto.CouponDTO;
import br.com.ecommerce.relogios.dto.CouponResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.service.CouponService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(value = "/api/coupon")
public class CouponResource {

    @Inject
    CouponService couponService;

    private static final Logger LOG = Logger.getLogger(CouponResource.class);

    //    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    public Response findAll() {
        try {
            LOG.info("Executando o findAll");
            List<CouponResponseDTO> coupons = couponService.findAll();
            LOG.info("Sucesso");
            return Response.ok(coupons).build();
        } catch (NotFoundException e) {
            LOG.warn("Coupons não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Coupons não encontrado").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o findById");
            CouponResponseDTO coupon = couponService.findCouponById(id);
            LOG.info("Coupon encontrado com sucesso");
            return Response.ok(coupon).build();
        } catch (NotFoundException e) {
            LOG.warn("Coupon não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Coupon não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao buscar Coupon", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Coupon").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @POST
    public Response create(CouponDTO couponDTO) {
        try {
            LOG.info("Executando o create");
            CouponResponseDTO coupon = couponService.create(couponDTO);
            LOG.info("Coupon criado com sucesso");
            return Response.status(Response.Status.CREATED).entity(coupon).build();
        } catch (ValidationException e) {
            LOG.error("Erro ao criar Coupon", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar Coupon").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CouponDTO couponDTO) {
        try {
            LOG.info("Executando o update");
            couponService.update(id, couponDTO);
            LOG.info("Coupon atualizado com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Coupon não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Coupon não encontrado").build();
        } catch (ValidationException e) {
            LOG.error("Erro ao atualizar Coupon", e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar Coupon").build();
        }
    }

    //    @RolesAllowed({"Admin", "Funcionario"})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Executando o delete Id: %s" + id.toString());
            couponService.delete(id);
            LOG.info("Coupon removido com sucesso");
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warn("Coupon não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity("Coupon não encontrado").build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar Coupon", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar Coupon").build();
        }
    }

    @GET
    @Path("/search/{code}")
    public Response findByCode(@PathParam("code") String code) {
        LOG.info("Executando o findByCode");
        CouponResponseDTO coupon = couponService.findByCode(code);
        LOG.info("Coupon encontrado com sucesso");
        return Response.ok(coupon).build();
    }

    @GET
    @Path("/valid")
    public Boolean valid(@QueryParam("id") Long id, @QueryParam("code") String code) {
        LOG.info("Executando o checkCodeUnique");
        return couponService.checkCodeUnique(id, code);
    }
}

