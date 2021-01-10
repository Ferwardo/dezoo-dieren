package com.dezoo.dieren.dierenservice.repositories;
import com.dezoo.dieren.dierenservice.models.DierModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DierenRepository extends JpaRepository<DierModel, Integer> {
    DierModel findDierModelByAnimalId(String animalId);
    List<DierModel> findDierModelByVertebrate(boolean isVertebrate);
    List<DierModel> findDierModelByClassification(String classification);
}
