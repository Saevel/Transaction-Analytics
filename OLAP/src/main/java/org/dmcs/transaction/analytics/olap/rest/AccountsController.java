package org.dmcs.transaction.analytics.olap.rest;

import org.dmcs.transaction.analytics.olap.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Zielony on 2016-08-10.
 */
@RestController("/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @RequestMapping(value = "/country/{country}/active", method = RequestMethod.GET,
    consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public Long countAccountsByCountry(@PathVariable("country") String country) {

        //TODO: Return true value
        return null;
    }

    @RequestMapping(value = "/country/{country}/balance", method = RequestMethod.GET,
    params = {"minAge", "maxAge"}, consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public Double averageAccountBalanceByCountryAndAge(@PathVariable("country") String country,
                                             @RequestParam("minAge") Integer minAge,
                                             @RequestParam("maxAge") Integer maxAge) {
        //TODO: Implement
        return null;
    }
}
