package com.gronazon.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.gronazon.service.CategoryService;
import com.gronazon.web.rest.errors.BadRequestAlertException;
import com.gronazon.web.util.HeaderUtil;
import com.gronazon.web.util.ResponseUtil;
import com.gronazon.service.dto.CategoryDTO;

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
 * REST controller for managing {@link com.gronazon.domain.Category}.
 */
@Path("/api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "category";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    CategoryService categoryService;
    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @param categoryDTO the categoryDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new categoryDTO, or with status {@code 400 (Bad Request)} if the category has already an ID.
     */
    @POST
    public Response createCategory(@Valid CategoryDTO categoryDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.id != null) {
            throw new BadRequestAlertException("A new category cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = categoryService.persistOrUpdate(categoryDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /categories} : Updates an existing category.
     *
     * @param categoryDTO the categoryDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated categoryDTO,
     * or with status {@code 400 (Bad Request)} if the categoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryDTO couldn't be updated.
     */
    @PUT
    public Response updateCategory(@Valid CategoryDTO categoryDTO) {
        log.debug("REST request to update Category : {}", categoryDTO);
        if (categoryDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = categoryService.persistOrUpdate(categoryDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" category.
     *
     * @param id the id of the categoryDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param pageRequest the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link Response} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GET
    public Response getAllCategories(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo, @QueryParam(value = "eagerload") boolean eagerload) {
        log.debug("REST request to get a page of Categories");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<CategoryDTO> result;
        if (eagerload) {
            result = categoryService.findAllWithEagerRelationships(page);
        } else {
            result = categoryService.findAll(page);
        }
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the categoryDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the categoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getCategory(@PathParam("id") Long id) {
        log.debug("REST request to get Category : {}", id);
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDTO);
    }
}
