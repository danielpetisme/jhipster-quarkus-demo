package com.gronazon.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.gronazon.service.CustomerService;
import com.gronazon.web.rest.errors.BadRequestAlertException;
import com.gronazon.web.util.HeaderUtil;
import com.gronazon.web.util.ResponseUtil;
import com.gronazon.service.dto.CustomerDTO;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gronazon.service.Paged;
import com.gronazon.web.rest.vm.PageRequestVM;
import com.gronazon.web.rest.vm.SortRequestVM;
import com.gronazon.web.util.PaginationUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gronazon.domain.Customer}.
 */
@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String ENTITY_NAME = "customer";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    CustomerService customerService;
    /**
     * {@code POST  /customers} : Create a new customer.
     *
     * @param customerDTO the customerDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new customerDTO, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     */
    @POST
    public Response createCustomer(@Valid CustomerDTO customerDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save Customer : {}", customerDTO);
        if (customerDTO.id != null) {
            throw new BadRequestAlertException("A new customer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = customerService.persistOrUpdate(customerDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /customers} : Updates an existing customer.
     *
     * @param customerDTO the customerDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated customerDTO,
     * or with status {@code 400 (Bad Request)} if the customerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerDTO couldn't be updated.
     */
    @PUT
    public Response updateCustomer(@Valid CustomerDTO customerDTO) {
        log.debug("REST request to update Customer : {}", customerDTO);
        if (customerDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = customerService.persistOrUpdate(customerDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /customers/:id} : delete the "id" customer.
     *
     * @param id the id of the customerDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GET
    public Response getAllCustomers(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of Customers");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<CustomerDTO> result = customerService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /customers/:id} : get the "id" customer.
     *
     * @param id the id of the customerDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the customerDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getCustomer(@PathParam("id") Long id) {
        log.debug("REST request to get Customer : {}", id);
        Optional<CustomerDTO> customerDTO = customerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerDTO);
    }
}
