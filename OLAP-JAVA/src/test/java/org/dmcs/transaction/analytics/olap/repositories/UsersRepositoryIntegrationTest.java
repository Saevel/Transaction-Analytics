package org.dmcs.transaction.analytics.classical.repositories;

import org.dmcs.transaction.analytics.classical.configuration.RepositoriesTestContext;
import org.dmcs.transaction.analytics.classical.model.UserData;
import org.dmcs.transaction.analytics.classical.test.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zielony on 2016-10-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoriesTestContext.class)
public class UsersRepositoryIntegrationTest {

    @Autowired
    private UsersRepository usersRepository;

    private List<UserData> users = Arrays.asList(
      new UserData(1, 30),
      new UserData(2, 40),
      new UserData(3, 25),
      new UserData(4, 55)
    );

    @Test
    public void shouldFindAllUserData(){ withUsers(() -> {
        Collection<UserData> retrievedUsers = usersRepository.findAllAsCollection();
        assertNotNull("There is no user data", retrievedUsers);
        assertTrue("Not all retrieved users were original users", users.containsAll(retrievedUsers));
        assertTrue("Not all original users were retrieved", retrievedUsers.containsAll(users));
    });}

    private void withUsers(Unit f) {
        usersRepository.save(users);
        f.apply();
        usersRepository.deleteAll();
    }
}