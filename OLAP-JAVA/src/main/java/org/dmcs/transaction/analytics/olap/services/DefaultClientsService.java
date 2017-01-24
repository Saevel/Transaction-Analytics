package org.dmcs.transaction.analytics.olap.services;

import org.dmcs.transaction.analytics.olap.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Zielony on 2016-08-10.
 */
@Service
public class DefaultClientsService implements ClientsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Long clientsAgeMedian() {
        List<Integer> sortedAges = usersRepository.findAllAsCollection().stream()
                .map((userData) -> userData.getAge())
                .sorted()
                .collect(Collectors.toList());

        Integer halfIndex = (int)Math.ceil((double)sortedAges.size() / 2.0);

        if(halfIndex == 0) {
            return 0L;
        }
        else {
           return sortedAges.get(halfIndex).longValue();
        }
    }

    @Override
    public Double clientAgeAverage() {
        return usersRepository.findAllAsCollection().stream()
                .mapToInt((userData) -> userData.getAge())
                .average()
                .orElse(0.0);
    }
}