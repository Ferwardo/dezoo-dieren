package com.dezoo.dieren.dierenservice.repositories;
import com.dezoo.dieren.dierenservice.models.DierModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DierenRepository extends JpaRepository<DierModel, Integer> {
    DierModel findDierModelByAnimalId(String animalId);
    List<DierModel> findDierModelByVertebrate(boolean isVertebrate);
    List<DierModel> findDierModelByClassification(String classification);
}
