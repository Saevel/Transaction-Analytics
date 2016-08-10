package org.dmcs.transaction.analytics.olap.rest;

import org.dmcs.transaction.analytics.olap.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zielony on 2016-08-10.
 */
@RestController("/clients")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;

    @RequestMapping(value = "/age/median", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public Integer clientsAgeMedian() {
        return null;
    }

    @RequestMapping(value = "/age/average", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public Double clientsAgeAverage() {
        return null;
    }
}
