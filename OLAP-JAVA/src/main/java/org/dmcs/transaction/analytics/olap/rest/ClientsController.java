package org.dmcs.transaction.analytics.classical.rest;

import org.dmcs.transaction.analytics.classical.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zielony on 2016-08-10.
 */
@RestController
public class ClientsController {

    @Autowired
    private ClientsService clientsService;

    @RequestMapping(value = "/clients/age/median", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Long> clientsAgeMedian() {
        return new HttpEntity<Long>(clientsService.clientsAgeMedian());
    }

    @RequestMapping(value = "/clients/age/average", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Double> clientsAgeAverage() {
        return new HttpEntity<>(clientsService.clientAgeAverage());
    }
}