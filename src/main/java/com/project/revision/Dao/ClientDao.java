package com.project.revision.Dao;

import com.project.revision.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends JpaRepository<Client,Long> {
Client findClientByUserEmail(String email);
}
