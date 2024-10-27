package com.Wkea.boots.repositories;

import java.util.List;

import com.Wkea.boots.entities.Boot;
import org.springframework.data.repository.CrudRepository;
import com.Wkea.boots.enums.BootType;


public interface BootRepository extends CrudRepository<Boot, Integer> {
    
}
