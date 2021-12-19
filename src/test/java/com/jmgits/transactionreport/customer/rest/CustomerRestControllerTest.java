package com.jmgits.transactionreport.customer.rest;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.jmgits.transactionreport.test.AbstractTest;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;

/**
 * Testing for {@link CustomerRestController}
 */
@Sql("/sql/customer.sql")
public class CustomerRestControllerTest extends AbstractTest {

    @Test
    public void testGetMeWithoutToken_Ko_Forbidden() {

        var response = restTemplate.exchange(
                "/api/v1/customers/me",
                GET,
                null,
                String.class
        );

        assertThat(response.getStatusCode(), is(FORBIDDEN));
    }

    @Test
    public void testGetMeWithInvalidToken_Ko_Unauthorized() {

        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer whatever test");

        var httpEntity = new HttpEntity<>(headers);

        var response = restTemplate.exchange(
                "/api/v1/customers/me",
                GET,
                httpEntity,
                String.class
        );

        assertThat(response.getStatusCode(), is(UNAUTHORIZED));

        assertThat(response.getBody(), allOf(
                hasJsonPath("errorCode", is(UNAUTHORIZED.name())),
                hasJsonPath("errorDescription", is("Invalid authorization."))
        ));
    }

    @Test
    public void testGetMeWithValidTokenButCustomerNotFound_Ko_NotFound() {

        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenHandler.generateToken("unknown.customer"));

        var httpEntity = new HttpEntity<>(headers);

        var response = restTemplate.exchange(
                "/api/v1/customers/me",
                GET,
                httpEntity,
                String.class
        );

        assertThat(response.getStatusCode(), is(NOT_FOUND));

        assertThat(response.getBody(), allOf(
                hasJsonPath("errorCode", is("CUSTOMER_NOT_FOUND")),
                hasJsonPath("errorDescription", is("Customer with id 'unknown.customer' not found."))
        ));
    }

    @Test
    public void testGetMeWithValidTokenAndCustomerFound_Ok() {

        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenHandler.generateToken("javier.moreno"));

        var httpEntity = new HttpEntity<>(headers);

        var response = restTemplate.exchange(
                "/api/v1/customers/me",
                GET,
                httpEntity,
                String.class
        );

        assertThat(response.getStatusCode(), is(OK));

        assertThat(response.getBody(), allOf(
                hasJsonPath("id", is("javier.moreno")),
                hasJsonPath("defaultCurrency", is("CHF"))
        ));
    }
}
