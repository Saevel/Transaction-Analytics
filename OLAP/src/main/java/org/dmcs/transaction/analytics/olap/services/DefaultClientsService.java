package org.dmcs.transaction.analytics.olap.services;

import org.dmcs.transaction.analytics.olap.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Zielony on 2016-08-10.
 */
@Service
public class DefaultClientsService implements ClientsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Long clientsAgeMedian() {
        //TODO: Implement
        usersRepository.findAll();
        return null;
    }

    @Override
    public Double clientAgeAverage() {
        //TODO: Implement
        usersRepository.findAll().iterator();
        return null;
    }
}
