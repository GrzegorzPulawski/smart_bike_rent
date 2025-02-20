package com.smart_bike_rent.repositories;

import com.smart_bike_rent.entity.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByPhoneNumber(Integer phoneNumber);
    List<Client> findByLastName(String lastName);
    List<Client> findAllByOrderByIdClientDesc();

}
