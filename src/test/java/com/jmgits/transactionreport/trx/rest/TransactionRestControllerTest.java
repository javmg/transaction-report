package com.jmgits.transactionreport.trx.rest;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.jmgits.transactionreport.test.AbstractTest;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;

/**
 * Testing for {@link TransactionRestController}
 */
@Sql("/sql/transaction.sql")
public class TransactionRestControllerTest extends AbstractTest {

    @Test
    public void testSearchWithoutToken_Ko_Forbidden() {

        var response = restTemplate.exchange(
                "/api/v1/transactions/search",
                GET,
                null,
                String.class
        );

        assertThat(response.getStatusCode(), is(FORBIDDEN));
    }

    @Test
    public void testSearchCustomerWithoutAccounts_Ko_Conflict() {

        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenHandler.generateToken("customer.no.accounts"));

        var response = restTemplate.exchange(
                "/api/v1/transactions/search",
                GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertThat(response.getStatusCode(), is(CONFLICT));

        assertThat(response.getBody(), allOf(
                hasJsonPath("errorCode", is("CUSTOMER_WITHOUT_ACCOUNTS")),
                hasJsonPath("errorDescription", is("This customer has no accounts."))
        ));
    }

    @Test
    public void testSearchCustomerUsingAccountNotOwned_Ko_NotFound() {

        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenHandler.generateToken("javier.moreno"));

        var response = restTemplate.exchange(
                "/api/v1/transactions/search?accountIbans=1111.2222.3333.4444",
                GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertThat(response.getStatusCode(), is(NOT_FOUND));

        assertThat(response.getBody(), allOf(
                hasJsonPath("errorCode", is("ACCOUNT_NOT_FOUND")),
                hasJsonPath("errorDescription", is("Account with IBAN '1111.2222.3333.4444' not found."))
        ));
    }

    @Test
    public void testSearchCustomerUsingOneAccounts_Ok() {

        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenHandler.generateToken("javier.moreno"));

        var response = restTemplate.exchange(
                "/api/v1/transactions/search?accountIbans=CH7512345678901234567&businessDateFrom=2021-01-01&businessDateTo=2021-01-01",
                GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertThat(response.getStatusCode(), is(OK));

        assertThat(response.getBody(), allOf(
                hasJsonPath("$.content", hasSize(1)),
                hasJsonPath("$.content[0].id", is("de8b7586-bc1e-40bb-bc19-dd8c6049f561")),
                hasJsonPath("$.content[0].originalAmount.currency", is("USD")),
                hasJsonPath("$.content[0].originalAmount.value", is(10)),
                hasJsonPath("$.content[0].convertedAmount.currency", is("CHF")),
                hasJsonPath("$.content[0].convertedAmount.value", is(100)),
                hasJsonPath("$.content[0].account.id", is("3afa799e-7812-4cfe-ae0a-a3a4d048f567")),
                hasJsonPath("$.content[0].account.iban", is("CH7512345678901234567")),
                hasJsonPath("$.content[0].account.bic", is("POFICHBEXXX")),
                hasJsonPath("$.content[0].account.bankName", is("PostFinance")),
                hasJsonPath("$.content[0].account.bankAddress", is("Bern")),
                hasJsonPath("$.content[0].businessDate", is("2021-01-01")),
                hasJsonPath("$.content[0].description", is("Amazon subscription"))
        ));
    }

    @Test
    public void testSearchCustomerUsingAllAccounts_Ok() {

        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenHandler.generateToken("javier.moreno"));

        var response = restTemplate.exchange(
                "/api/v1/transactions/search?businessDateFrom=2021-01-01&businessDateTo=2021-01-01",
                GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertThat(response.getStatusCode(), is(OK));

        assertThat(response.getBody(), allOf(
                hasJsonPath("$.content", hasSize(2)),
                hasJsonPath("$.content[0].id", is("de8b7586-bc1e-40bb-bc19-dd8c6049f561")),
                hasJsonPath("$.content[1].id", is("76ec6eef-0c57-49e4-bdb0-fc4bc217158f"))
        ));
    }
}
