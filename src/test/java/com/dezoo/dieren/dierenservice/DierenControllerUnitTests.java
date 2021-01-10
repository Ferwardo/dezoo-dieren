package com.dezoo.dieren.dierenservice;

import com.dezoo.dieren.dierenservice.models.DierModel;
import com.dezoo.dieren.dierenservice.repositories.DierenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class DierenControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DierenRepository dierenRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

//    DierModel dierModel1 = new DierModel("lion1", "Simba", "Lion", format.parse("03/03/2003"), true, "Mammal"),
//            dierModel2 = new DierModel("rabbit1", "Stamper", "Rabbit", format.parse("03/11/2016"), true, "Mammal"),
//            dierModelToBeDeleted = new DierModel("parrot1", "Coco Granata", "Parrot", format.parse("11/05/1998"), true, "Bird");

    @Test
    public void givenDier_whenGetDier_ThenReturnJsonDieren() throws Exception {
        DierModel dierModel1 = new DierModel("lion1", "Simba", "Lion", format.parse("03/03/2003"), true, "Mammal"),
                dierModel2 = new DierModel("rabbit1", "Stamper", "Rabbit", format.parse("03/11/2016"), true, "Mammal"),
                dierModel3 = new DierModel("parrot1", "Coco Granata", "Parrot", format.parse("11/05/1998"), true, "Bird");

        List<DierModel> animalList = new ArrayList<>();
        animalList.add(dierModel1);
        animalList.add(dierModel2);
        animalList.add(dierModel3);

        given(dierenRepository.findAll()).willReturn(animalList);

        mockMvc.perform(get("/animals"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[0].animalId", is("lion1")))
                .andExpect(jsonPath("[0].name", is("Simba")))
                .andExpect(jsonPath("[0].kind", is("Lion")))
                .andExpect(jsonPath("[0].dateOfBirth", is("2003-03-02T23:00:00.000+00:00")))
                .andExpect(jsonPath("[0].vertebrate", is(true)))
                .andExpect(jsonPath("[0].classification", is("Mammal")))

                .andExpect(jsonPath("[1].animalId", is("rabbit1")))
                .andExpect(jsonPath("[1].name", is("Stamper")))
                .andExpect(jsonPath("[1].kind", is("Rabbit")))
                .andExpect(jsonPath("[1].dateOfBirth", is("2016-11-02T23:00:00.000+00:00")))
                .andExpect(jsonPath("[1].vertebrate", is(true)))
                .andExpect(jsonPath("[1].classification", is("Mammal")))

                .andExpect(jsonPath("[2].animalId", is("parrot1")))
                .andExpect(jsonPath("[2].name", is("Coco Granata")))
                .andExpect(jsonPath("[2].kind", is("Parrot")))
                .andExpect(jsonPath("[2].dateOfBirth", is("1998-05-10T22:00:00.000+00:00")))
                .andExpect(jsonPath("[2].vertebrate", is(true)))
                .andExpect(jsonPath("[2].classification", is("Bird")));
    }

    @Test
    public void givenDier_whenGetDierByID_ThenReturnJsonDier() throws Exception {
        DierModel dierModel = new DierModel("lion1", "Simba", "Lion", format.parse("03/03/2003"), true, "Mammal");

        given(dierenRepository.findDierModelByAnimalId("lion1")).willReturn(dierModel);

        mockMvc.perform(get("/animals/{animalID}", "lion1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", is("lion1")))
                .andExpect(jsonPath("$.name", is("Simba")))
                .andExpect(jsonPath("$.kind", is("Lion")))
                .andExpect(jsonPath("$.dateOfBirth", is("2003-03-02T23:00:00.000+00:00")))
                .andExpect(jsonPath("$.vertebrate", is(true)))
                .andExpect(jsonPath("$.classification", is("Mammal")));
    }

    @Test
    public void whenPostDier_ThenReturnJsonDier() throws Exception {
        DierModel dierModel = new DierModel("stickbug1", "Sticker", "Stickbug", format.parse("29/11/2020"), false, "Insect");

        mockMvc.perform(post("/animals")
                .content(mapper.writeValueAsString(dierModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenDier_whenPutDier_ThenReturnJsonDier() throws Exception {
        DierModel dierModel = new DierModel("lion1", "Simba", "Lion", format.parse("03/03/2003"), true, "Mammal");

        given(dierenRepository.findDierModelByAnimalId("lion1")).willReturn(dierModel);

        DierModel updateDierModel = new DierModel("lion1", "Mufasa", "Lion", format.parse("03/03/2003"), true, "Mammal");

        mockMvc.perform(put("/animals/{animalID}", "lion1")
                .content(mapper.writeValueAsString(updateDierModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", is("lion1")))
                .andExpect(jsonPath("$.Name", is("Mufasa")))
                .andExpect(jsonPath("$.kind", is("Lion")))
                .andExpect(jsonPath("$.dateOfBirth", is("2003-03-03T00:00:00.000+00:00")))
                .andExpect(jsonPath("$.vertebrate", is(true)))
                .andExpect(jsonPath("$.classification", is("Mammal")));
    }

    @Test
    public void givenDier_WhenDeleteDier_ThenStatusOk() throws Exception {
        DierModel dierModel = new DierModel("parrot1", "Kaketoe", "parrot", format.parse("03/03/2003"), true, "Bird");

        given(dierenRepository.findDierModelByAnimalId("parrot1")).willReturn(dierModel);

        mockMvc.perform(delete("/animals/{animalID}", "parrot1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoDier_whenDeleteDier_ThenStatusNotFound() throws Exception {
        given(dierenRepository.findDierModelByAnimalId("parrot2")).willReturn(null);

        mockMvc.perform(delete("/animals/{animalID}", "parrot2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
