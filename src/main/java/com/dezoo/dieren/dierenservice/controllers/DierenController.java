package com.dezoo.dieren.dierenservice.controllers;


import com.dezoo.dieren.dierenservice.models.DierModel;
import com.dezoo.dieren.dierenservice.repositories.DierenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("animals")
public class DierenController {
    @Autowired
    private DierenRepository dierenRepository;

    @GetMapping
    public List<DierModel> getAnimals(){
        return dierenRepository.findAll();
    }

    @GetMapping("/{animalID}")
    public DierModel getAnimalID(@PathVariable String animalId){
        return dierenRepository.findDierModelByAnimalId(animalId);
    }

    @GetMapping("/getVertebrates")
    public List<DierModel> getVertebrates(){
        return dierenRepository.findDierModelByVertebrate(true);
    }

    @GetMapping("/{classification}")
    public List<DierModel> getAnimalsInClassification(@PathVariable String classification){
        return dierenRepository.findDierModelByClassification(classification);
    }

    @PostMapping
    public void postAnimal(@RequestBody DierModel dierModel){
        dierenRepository.save(dierModel);
    }

    @PutMapping("/{animalID}")
    public ResponseEntity<DierModel> putAnimal(@PathVariable String animalID, @RequestBody DierModel changedDierModel){
        DierModel dierModel = dierenRepository.findDierModelByAnimalId(animalID);

        dierModel.setClassification(changedDierModel.getClassification());
        dierModel.setDateOfBirth(changedDierModel.getDateOfBirth());
        dierModel.setKind(changedDierModel.getKind());
        dierModel.setName(changedDierModel.getName());
        dierModel.setVertebrate(changedDierModel.isVertebrate());

        dierModel = dierenRepository.save(dierModel);
        return ResponseEntity.ok(dierModel);

    }

    @DeleteMapping("/{animalID}")
    public ResponseEntity<DierModel> deleteAnimal(@PathVariable String animalID){
        DierModel dierModel = dierenRepository.findDierModelByAnimalId(animalID);
        dierenRepository.delete(dierModel);

        return ResponseEntity.ok(dierModel);
    }
    @PostConstruct
    public void fillDBwithTestData() {
        dierenRepository.save(new DierModel("r001", "Flappie", "Rabbit", new Date("16/04/2017"), true, "mammal"));
        dierenRepository.save(new DierModel("l001", "Simba", "Lion", new Date("24/07/2011"), true, "mammal"));
        dierenRepository.save(new DierModel("s001", "Scrat", "Squirrel", new Date("04/01/2019"), true, "mammal"));
    }

}
