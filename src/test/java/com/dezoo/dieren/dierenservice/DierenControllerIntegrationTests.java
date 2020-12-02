package com.dezoo.dieren.dierenservice;

import com.dezoo.dieren.dierenservice.models.DierModel;
import com.dezoo.dieren.dierenservice.repositories.DierenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DierenControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DierenRepository dierenRepository;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    DierModel dierModel1 = new DierModel("lion1", "Simba", "Lion", format.parse("03/03/2003"), true, "Mammal"),
    dierModel2 = new DierModel("rabbit1", "Stamper", "Rabbit", format.parse("03/11/2016"), true, "Mammal"),
    dierModelToBeDeleted = new DierModel("parrot1", "Coco Granata", "Parrot", format.parse("11/05/1998"), true, "Bird");

    public DierenControllerIntegrationTests() throws ParseException {
    }

    @BeforeEach
    public void beforeAllTests(){
        dierenRepository.save(dierModel1);
        dierenRepository.save(dierModel2);
        dierenRepository.save(dierModelToBeDeleted);
    }

    @AfterEach
    public void afterAllTests(){
        dierenRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenDier_whenGetDier_ThenReturnJsonDieren() throws Exception{
        mockMvc.perform(get("/dieren"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[0].animalId", is("lion1")))
                .andExpect(jsonPath("[0].Name", is("Simba")))
                .andExpect(jsonPath("[0].kind", is("Lion")))
                .andExpect(jsonPath("[0].dateOfBirth", is("2003-03-03T00:00:00.000+00:00")))
                .andExpect(jsonPath("[0].vertebrate", is("true")))
                .andExpect(jsonPath("[0].classification", is("Mammal")))

                .andExpect(jsonPath("[0].animalId", is("rabbit1")))
                .andExpect(jsonPath("[0].Name", is("Stamper")))
                .andExpect(jsonPath("[0].kind", is("Rabbit")))
                .andExpect(jsonPath("[0].dateOfBirth", is("2016-11-03T00:00:00.000+00:00")))
                .andExpect(jsonPath("[0].vertebrate", is("true")))
                .andExpect(jsonPath("[0].classification", is("Mammal")))

                .andExpect(jsonPath("[0].animalId", is("parrot1")))
                .andExpect(jsonPath("[0].Name", is("Coco Granata")))
                .andExpect(jsonPath("[0].kind", is("Parrot")))
                .andExpect(jsonPath("[0].dateOfBirth", is("1998-05-11T00:00:00.000+00:00")))
                .andExpect(jsonPath("[0].vertebrate", is("true")))
                .andExpect(jsonPath("[0].classification", is("Bird")));



    }
}
