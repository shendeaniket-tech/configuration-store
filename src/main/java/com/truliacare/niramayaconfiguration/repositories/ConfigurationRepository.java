package com.truliacare.niramayaconfiguration.repositories;

import com.truliacare.niramayaconfiguration.models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer>  {
    Optional<Configuration> findByIdentifier(Integer identifier);
}
