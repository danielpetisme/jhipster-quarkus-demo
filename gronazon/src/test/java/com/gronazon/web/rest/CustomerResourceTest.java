package com.gronazon.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.gronazon.TestUtil;
import com.gronazon.service.dto.CustomerDTO;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

    import java.util.List;

@QuarkusTest
public class CustomerResourceTest {

    private static final TypeRef<CustomerDTO> ENTITY_TYPE = new TypeRef<>() {
    };

    private static final TypeRef<List<CustomerDTO>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {
    };

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";



    String adminToken;

    CustomerDTO customerDTO;

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
    public static CustomerDTO createEntity() {
        var customerDTO = new CustomerDTO();
        customerDTO.firstName = DEFAULT_FIRST_NAME;
        customerDTO.lastName = DEFAULT_LAST_NAME;
        customerDTO.email = DEFAULT_EMAIL;
        customerDTO.telephone = DEFAULT_TELEPHONE;
        return customerDTO;
    }

    @BeforeEach
    public void initTest() {
        customerDTO = createEntity();
    }

    @Test
    public void createCustomer() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the Customer
        customerDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(customerDTO)
            .when()
            .post("/api/customers")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Validate the Customer in the database
        var customerDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(customerDTOList).hasSize(databaseSizeBeforeCreate + 1);
        var testCustomerDTO = customerDTOList.stream().filter(it -> customerDTO.id.equals(it.id)).findFirst().get();
        assertThat(testCustomerDTO.firstName).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerDTO.lastName).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerDTO.email).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerDTO.telephone).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    public void createCustomerWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the Customer with an existing ID
        customerDTO.id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(customerDTO)
            .when()
            .post("/api/customers")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Customer in the database
        var customerDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(customerDTOList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void updateCustomer() {
        // Initialize the database
        customerDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(customerDTO)
            .when()
            .post("/api/customers")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the customer
        var updatedCustomerDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers/{id}", customerDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().body().as(ENTITY_TYPE);

        // Update the customer
        updatedCustomerDTO.firstName = UPDATED_FIRST_NAME;
        updatedCustomerDTO.lastName = UPDATED_LAST_NAME;
        updatedCustomerDTO.email = UPDATED_EMAIL;
        updatedCustomerDTO.telephone = UPDATED_TELEPHONE;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedCustomerDTO)
            .when()
            .put("/api/customers")
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the Customer in the database
        var customerDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(customerDTOList).hasSize(databaseSizeBeforeUpdate);
        var testCustomerDTO = customerDTOList.stream().filter(it -> updatedCustomerDTO.id.equals(it.id)).findFirst().get();
        assertThat(testCustomerDTO.firstName).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerDTO.lastName).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerDTO.email).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerDTO.telephone).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    public void updateNonExistingCustomer() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
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
            .body(customerDTO)
            .when()
            .put("/api/customers")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Customer in the database
        var customerDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(customerDTOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCustomer() {
        // Initialize the database
        customerDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(customerDTO)
            .when()
            .post("/api/customers")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the customer
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/customers/{id}", customerDTO.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var customerDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(customerDTOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAllCustomers() {
        // Initialize the database
        customerDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(customerDTO)
            .when()
            .post("/api/customers")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Get all the customerList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", hasItem(customerDTO.id.intValue()))
            .body("firstName", hasItem(DEFAULT_FIRST_NAME))            .body("lastName", hasItem(DEFAULT_LAST_NAME))            .body("email", hasItem(DEFAULT_EMAIL))            .body("telephone", hasItem(DEFAULT_TELEPHONE));
    }

    @Test
    public void getCustomer() {
        // Initialize the database
        customerDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(customerDTO)
            .when()
            .post("/api/customers")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var response = // Get the customer
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/customers/{id}", customerDTO.id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract().as(ENTITY_TYPE);

        // Get the customer
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers/{id}", customerDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is(customerDTO.id.intValue()))
            
                .body("firstName", is(DEFAULT_FIRST_NAME))
                .body("lastName", is(DEFAULT_LAST_NAME))
                .body("email", is(DEFAULT_EMAIL))
                .body("telephone", is(DEFAULT_TELEPHONE));
    }

    @Test
    public void getNonExistingCustomer() {
        // Get the customer
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/customers/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
