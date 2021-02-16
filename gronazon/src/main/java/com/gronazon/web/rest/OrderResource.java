package com.gronazon.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.gronazon.service.OrderService;
import com.gronazon.web.rest.errors.BadRequestAlertException;
import com.gronazon.web.util.HeaderUtil;
import com.gronazon.web.util.ResponseUtil;
import com.gronazon.service.dto.OrderDTO;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gronazon.service.Paged;
import com.gronazon.web.rest.vm.PageRequestVM;
import com.gronazon.web.rest.vm.SortRequestVM;
import com.gronazon.web.util.PaginationUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gronazon.domain.Order}.
 */
@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    OrderService orderService;
    /**
     * {@code POST  /orders} : Create a new order.
     *
     * @param orderDTO the orderDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new orderDTO, or with status {@code 400 (Bad Request)} if the order has already an ID.
     */
    @POST
    public Response createOrder(OrderDTO orderDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save Order : {}", orderDTO);
        if (orderDTO.id != null) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = orderService.persistOrUpdate(orderDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /orders} : Updates an existing order.
     *
     * @param orderDTO the orderDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated orderDTO,
     * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
     */
    @PUT
    public Response updateOrder(OrderDTO orderDTO) {
        log.debug("REST request to update Order : {}", orderDTO);
        if (orderDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = orderService.persistOrUpdate(orderDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /orders/:id} : delete the "id" order.
     *
     * @param id the id of the orderDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") Long id) {
        log.debug("REST request to delete Order : {}", id);
        orderService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GET
    public Response getAllOrders(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of Orders");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<OrderDTO> result = orderService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /orders/:id} : get the "id" order.
     *
     * @param id the id of the orderDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the orderDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getOrder(@PathParam("id") Long id) {
        log.debug("REST request to get Order : {}", id);
        Optional<OrderDTO> orderDTO = orderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDTO);
    }
}
