package org.dmcs.transaction.analytics.olap.repositories;

import org.dmcs.transaction.analytics.olap.model.UserAccount;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Created by Zielony on 2016-08-10.
 */
@Repository
public interface AccountsRepository extends CrudRepository<UserAccount, Long> {

    @Query("SELECT COUNT(*) FROM USER_ACCOUNT WHERE country = ?0")
    Long countByCountry(@Param("country") String country);

    @Query("SELECT * FROM USER_ACCOUNT WHERE country = ?0")
    Collection<UserAccount> findByCountry(@Param("country") String country);

    @Query("SELECT * FROM USER_ACCOUNT WHERE country = ?0 AND age >= ?1 ALLOW FILTERING")
    Collection<UserAccount> findByCountryAndMinAge(@Param("country") String country, @Param("minAge") Integer minAge);

    @Query("SELECT * FROM USER_ACCOUNT WHERE country = ?0 AND age <= ?1 ALLOW FILTERING")
    Collection<UserAccount> findByCountryAndMaxAge(@Param("country") String country, @Param("maxAge") Integer maxAge);

    @Query("SELECT * FROM USER_ACCOUNT WHERE country = ?0 AND age >= ?1 AND age <= ?2 ALLOW FILTERING")
    Collection<UserAccount> findByCountryAndAgeInterval(@Param("country") String country, @Param("minAge")Integer minAge,
                                                    @Param("maxAge") Integer maxAge);
}