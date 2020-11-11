package com.dezoo.dieren.dierenservice.repositories;
import com.dezoo.dieren.dierenservice.models.DierModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DierenRepository extends JpaRepository<DierModel, Integer> {
    DierModel findDierModelByAnimalId(String animalId);
}
