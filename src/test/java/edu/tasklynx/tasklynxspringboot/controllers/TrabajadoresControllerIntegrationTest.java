package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS, scripts = "classpath:init.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrabajadoresControllerIntegrationTest {
    // region Previous setup
    final String URL = "/api/trabajadores";

    @Autowired
    private TestRestTemplate restTemplate;

    private Trabajador newTrabajador = new Trabajador(
            "T800",
            "80080080T",
            "Arnaldo",
            "Charcheneguer",
            "sayonara@volvere.com",
            "Dame tu ropa",
            "Espacio-Tiempo"
    );
    // endregion

    @Test
    @Order(1)
    public void findAllShouldGetThree() {
        ResponseEntity<Map> response = restTemplate.getForEntity(URL, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().get("error"));
        assertEquals(3, ((List) response.getBody().get("result")).size());
    }

    @Test
    @Order(2)
    public void findByIdShouldFail() {
        ResponseEntity<String> response = restTemplate.getForEntity(URL + "/" + newTrabajador.getIdTrabajador(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void findByIdAndPassShouldFail() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                URL +
                        "/" + newTrabajador.getIdTrabajador() +
                        "/" + newTrabajador.getContraseña(),
                String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(4)
    public void findBySpecialityShouldGetOne() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                URL + "/especialidad/Carpinteria",
                Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().get("error"));
        assertEquals(1, ((List) response.getBody().get("result")).size());
    }


    @Test
    @Order(3)
    public void createShouldSucceed() {
        ResponseEntity<Map> response = restTemplate.postForEntity(URL, newTrabajador, Map.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(false, response.getBody().get("error"));
        assertEquals(newTrabajador.getIdTrabajador(), ((Map) response.getBody().get("result")).get("idTrabajador"));
    }

    @Test
    @Order(4)
    public void createShouldFail() {
        ResponseEntity<String> response = restTemplate.postForEntity(URL, newTrabajador, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void findByIdSucceeds() {
        ResponseEntity<Map> response = restTemplate.getForEntity(URL, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().get("error"));
        assertEquals(4, ((List) response.getBody().get("result")).size());
    }

    @Test
    @Order(6)
    public void findAllShouldGetFour() {
        ResponseEntity<Map> response = restTemplate.getForEntity(URL, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().get("error"));
        assertEquals(4, ((List) response.getBody().get("result")).size());
    }

    @Test
    @Order(7)
    public void updateShouldSucceed() {
        newTrabajador.setNombre("Arnoldo");

        restTemplate.put(URL + "/" + newTrabajador.getIdTrabajador(), newTrabajador);

        ResponseEntity<Map> response = restTemplate.getForEntity(URL + "/" + newTrabajador.getIdTrabajador(), Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().get("error"));
        assertEquals("Arnoldo", ((Map) response.getBody().get("result")).get("nombre"));
    }

    @Test
    @Order(8)
    public void updateShouldFail() {
        newTrabajador.setEspecialidad(null);

        ResponseEntity<String> response = restTemplate.exchange(
                URL + "/" + newTrabajador.getIdTrabajador(),
                HttpMethod.PUT,
                new HttpEntity<>(newTrabajador),
                String.class
        );

        System.out.println(response.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(20)
    public void deleteShouldSucceed() {
        System.out.println("Deleting: " + newTrabajador.getIdTrabajador());

        restTemplate.delete(URL + "/" + newTrabajador.getIdTrabajador() + "/eliminar");

        ResponseEntity<Map> response = restTemplate.getForEntity(URL, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().get("error"));
        assertEquals(3, ((List) response.getBody().get("result")).size());
    }
}