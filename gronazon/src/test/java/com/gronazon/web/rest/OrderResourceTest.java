package com.gronazon.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.gronazon.TestUtil;
import com.gronazon.service.dto.OrderDTO;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

    import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@QuarkusTest
public class OrderResourceTest {

    private static final TypeRef<OrderDTO> ENTITY_TYPE = new TypeRef<>() {
    };

    private static final TypeRef<List<OrderDTO>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {
    };

    private static final OrderStatus DEFAULT_STATUS = CREATED;
    private static final OrderStatus UPDATED_STATUS = IN_PROGRESS;

    private static final Instant DEFAULT_DATE_ADDED = Instant.ofEpochSecond(0L).truncatedTo(ChronoUnit.SECONDS);
    private static final Instant UPDATED_DATE_ADDED = Instant.now().truncatedTo(ChronoUnit.SECONDS);

    private static final Instant DEFAULT_DATE_MODIFIED = Instant.ofEpochSecond(0L).truncatedTo(ChronoUnit.SECONDS);
    private static final Instant UPDATED_DATE_MODIFIED = Instant.now().truncatedTo(ChronoUnit.SECONDS);



    String adminToken;

    OrderDTO orderDTO;

    @Inject
    LiquibaseFactory liquibaseFactory;

    @BeforeAll
    static void jsonMapper() {
        RestAssured.config =
            RestAssured.config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(TestUtil.jsonbObjectMapper()));
    }

    @BeforeEach
    public void authenticateAdmin() {
        this.adminToken = TestUtil.getAdminToken();
    }

    @BeforeEach
    public void databaseFixture() {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDTO createEntity() {
        var orderDTO = new OrderDTO();
        orderDTO.status = DEFAULT_STATUS;
        orderDTO.dateAdded = DEFAULT_DATE_ADDED;
        orderDTO.dateModified = DEFAULT_DATE_MODIFIED;
        return orderDTO;
    }

    @BeforeEach
    public void initTest() {
        orderDTO = createEntity();
    }

    @Test
    public void createOrder() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the Order
        orderDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(orderDTO)
            .when()
            .post("/api/orders")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Validate the Order in the database
        var orderDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(orderDTOList).hasSize(databaseSizeBeforeCreate + 1);
        var testOrderDTO = orderDTOList.stream().filter(it -> orderDTO.id.equals(it.id)).findFirst().get();
        assertThat(testOrderDTO.status).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderDTO.dateAdded).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testOrderDTO.dateModified).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    public void createOrderWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the Order with an existing ID
        orderDTO.id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(orderDTO)
            .when()
            .post("/api/orders")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Order in the database
        var orderDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(orderDTOList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void updateOrder() {
        // Initialize the database
        orderDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(orderDTO)
            .when()
            .post("/api/orders")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the order
        var updatedOrderDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders/{id}", orderDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().body().as(ENTITY_TYPE);

        // Update the order
        updatedOrderDTO.status = UPDATED_STATUS;
        updatedOrderDTO.dateAdded = UPDATED_DATE_ADDED;
        updatedOrderDTO.dateModified = UPDATED_DATE_MODIFIED;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedOrderDTO)
            .when()
            .put("/api/orders")
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the Order in the database
        var orderDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(orderDTOList).hasSize(databaseSizeBeforeUpdate);
        var testOrderDTO = orderDTOList.stream().filter(it -> updatedOrderDTO.id.equals(it.id)).findFirst().get();
        assertThat(testOrderDTO.status).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderDTO.dateAdded).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testOrderDTO.dateModified).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    public void updateNonExistingOrder() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(orderDTO)
            .when()
            .put("/api/orders")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Order in the database
        var orderDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(orderDTOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteOrder() {
        // Initialize the database
        orderDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(orderDTO)
            .when()
            .post("/api/orders")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the order
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/orders/{id}", orderDTO.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var orderDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(orderDTOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAllOrders() {
        // Initialize the database
        orderDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(orderDTO)
            .when()
            .post("/api/orders")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Get all the orderList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", hasItem(orderDTO.id.intValue()))
            .body("status", hasItem(DEFAULT_STATUS.toString()))            .body("dateAdded", hasItem(TestUtil.formatDateTime(DEFAULT_DATE_ADDED)))            .body("dateModified", hasItem(TestUtil.formatDateTime(DEFAULT_DATE_MODIFIED)));
    }

    @Test
    public void getOrder() {
        // Initialize the database
        orderDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(orderDTO)
            .when()
            .post("/api/orders")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var response = // Get the order
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/orders/{id}", orderDTO.id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract().as(ENTITY_TYPE);

        // Get the order
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders/{id}", orderDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is(orderDTO.id.intValue()))
            
                .body("status", is(DEFAULT_STATUS.toString()))
                .body("dateAdded", is(TestUtil.formatDateTime(DEFAULT_DATE_ADDED)))
                .body("dateModified", is(TestUtil.formatDateTime(DEFAULT_DATE_MODIFIED)));
    }

    @Test
    public void getNonExistingOrder() {
        // Get the order
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/orders/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
