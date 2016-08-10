package org.dmcs.transaction.analytics.olap.rest;

import org.dmcs.transaction.analytics.olap.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by Zielony on 2016-08-10.
 */
@RestController("/capital")
public class CapitalController {

    @Autowired
    private TransactionsService transactionsService;

    @RequestMapping(value = "/variance", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE,
    produces = MediaType.TEXT_PLAIN_VALUE, params = {"start", "end"})
    public Double capitalVarianceInPeriod(@RequestParam("start") LocalDateTime start, @RequestParam LocalDateTime end) {
        return null;
    }

    @RequestMapping(value = "/insertions/average", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE, params = {"start", "end"})
    public Double averageInsertionInPeriod(@RequestParam("start") LocalDateTime start, @RequestParam LocalDateTime end) {
        return null;
    }

    @RequestMapping(value = "/withdrawals/average", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE, params = {"start", "end"})
    public Double averageWithdrawalInPeriod(@RequestParam("start") LocalDateTime start, @RequestParam LocalDateTime end) {
        return null;
    }
}