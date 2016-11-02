package org.dmcs.transaction.analytics.olap.rest;

import org.dmcs.transaction.analytics.olap.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Created by Zielony on 2016-08-10.
 */
@RestController
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @RequestMapping(value = "/accounts/country/{country}/active", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Long> countAccountsByCountry(@PathVariable("country") String country) {
        return new HttpEntity<>(accountsService.countAccountsByCountry(country));
    }

    @RequestMapping(value = "/accounts/country/{country}/balance", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Double> averageAccountBalanceByCountryAndAge(@PathVariable("country") String country,
                                             @RequestParam(name = "minAge", required = false) Optional<Integer> minAge,
                                             @RequestParam(name = "maxAge", required = false) Optional<Integer> maxAge) {
        return new HttpEntity<>(accountsService.averageAccountsBalanceByCountryAndAge(country, minAge, maxAge));
    }
}