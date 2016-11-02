package org.dmcs.transaction.analytics.olap.rest;

import org.dmcs.transaction.analytics.olap.configuration.ApplicationContext;
import org.dmcs.transaction.analytics.olap.configuration.RepositoriesTestContext;
import org.dmcs.transaction.analytics.olap.configuration.ServicesContext;
import org.dmcs.transaction.analytics.olap.model.UserData;
import org.dmcs.transaction.analytics.olap.repositories.UsersRepository;
import org.dmcs.transaction.analytics.olap.test.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zielony on 2016-10-20.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ,
        classes = {RepositoriesTestContext.class, ServicesContext.class, ApplicationContext.class})
@RunWith(SpringRunner.class)
public class ClientsResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsersRepository usersRepository;

    private List<UserData> users = Arrays.asList(
            new UserData(1, 35),
            new UserData(2, 40),
            new UserData(4, 45),
            new UserData(5, 25),
            new UserData(6, 55)
    );

    @Test
    public void shouldCalculateClientsAgeMedian(){ withUsers(() -> {
        String median = restTemplate.getForObject("/clients/age/median", String.class);
        assertNotNull("Null result was not returned", median);
        assertEquals("The median value was not correct", 45, Long.parseLong(median));
    });}

    @Test
    public void shouldCalculateClientsAgeAverage(){ withUsers(() -> {
       String average = restTemplate.getForObject("/clients/age/average", String.class);
        assertNotNull("Null result was returned", average);
        assertEquals("The average was not correct", 40.0, Double.parseDouble(average), 0.1);
    });}

    private void withUsers(Unit f) {
        usersRepository.save(users);
        f.apply();
        usersRepository.deleteAll();
    }
}