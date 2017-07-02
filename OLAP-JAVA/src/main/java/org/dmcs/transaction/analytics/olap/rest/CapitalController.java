package org.dmcs.transaction.analytics.classical.rest;

import org.dmcs.transaction.analytics.classical.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * Created by Zielony on 2016-08-10.
 */
@RestController
public class CapitalController {

    @Autowired
    private TransactionsService transactionsService;

    @RequestMapping(value = "/capital/variance", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE,
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity<Double> capitalVarianceInPeriod(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "start", required = false) Optional<LocalDateTime> start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "end", required = false) Optional<LocalDateTime> end) {
        return new HttpEntity<>(transactionsService.capitalVarianceInPeriod(
                start.map((date) -> Timestamp.valueOf(date)),
                end.map((date) -> Timestamp.valueOf(date))
        ));
    }

    @RequestMapping(value = "/capital/insertions/average", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Double> averageInsertionInPeriod(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "start", required = false) Optional<LocalDateTime> start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "end", required = false) Optional<LocalDateTime> end) {
        return new HttpEntity(transactionsService.averageInsertionInPeriod(
                start.map((date) -> Timestamp.valueOf(date)),
                end.map((date) -> Timestamp.valueOf(date))
        ));
    }

    @RequestMapping(value = "/capital/withdrawals/average", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Double> averageWithdrawalInPeriod(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "start", required = false) Optional<LocalDateTime> start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "end", required = false) Optional<LocalDateTime> end) {
        return new HttpEntity<>(transactionsService.averageWithdrawalInPeriod(
                start.map((date) -> Timestamp.valueOf(date)),
                end.map((date) -> Timestamp.valueOf(date))
        ));
    }
}