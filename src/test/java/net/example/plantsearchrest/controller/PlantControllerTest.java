package net.example.plantsearchrest.controller;

import net.example.plantsearchrest.TestUtils;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.model.PlantFilterDataModel;
import net.example.plantsearchrest.model.PlantFilterModel;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.Messages;
import net.example.plantsearchrest.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@WebMvcTest(PlantController.class)
public class PlantControllerTest {
    @Autowired
    private WebApplicationContext context;
    private String PLANT_URL = "http://localhost:8085/api/v1/plants";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private PlantService plantService;
    @MockBean
    private Messages messages;
    @Mock
    private UserUtil userUtil;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getPlantList_Success() throws Exception {
         when(plantService.getAll()).thenReturn(new ArrayList<>() {{add(new PlantPreviewDto(1)); add(new PlantPreviewDto(2)); add(new PlantPreviewDto(3)); add(new PlantPreviewDto(4));}});

         mockMvc.perform(get(PLANT_URL)
                         .contentType(MediaType.APPLICATION_JSON)
                         .param("page", "1")
                         .param("size", "5"))
                 .andDo(print())
                 .andExpect(status().isOk());
                 //.andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value("1"))
                 //.andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value("20"));
    }

    @Test
    public void getPlantById_Success() throws Exception {
        when(plantService.getById(anyLong())).thenReturn(new PlantDto());

        mockMvc.perform(get(PLANT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getPlantById_NotFound() throws  Exception {
        when(plantService.getById(anyLong())).thenThrow(new NotFoundException("Not found.", "NOT_FOUND"));

        mockMvc.perform(get(PLANT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetRandomPlantList_Success() throws Exception {
        when(plantService.getRandom(anyInt())).thenReturn(List.of(new PlantPreviewDto()));

        mockMvc.perform(get(PLANT_URL + "/random")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("amount", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchPlantsByName_Success() throws Exception {
        when(plantService.findByMatchingName(anyString())).thenReturn(List.of(new PlantPreviewDto()));

        mockMvc.perform(get(PLANT_URL + "/random")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("keyword", "test")
                        .param("page", "1")
                        .param("size", "20"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testFilterPlants_Success() throws Exception {
        PlantFilterModel plantFilterModel = new PlantFilterModel();
        PlantFilterDataModel plantFilterDataModel = new PlantFilterDataModel();
        plantFilterModel.setData(plantFilterDataModel);

        when(plantService.getAllByCriterias(any())).thenReturn(List.of(new PlantPreviewDto()));

        mockMvc.perform(post(PLANT_URL + "/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "20")
                        .content(TestUtils.asJsonString(plantFilterModel)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = "ADMIN")
    public void testCreatePlant_Success() throws Exception {
        when(plantService.create(any(), any(), any())).thenReturn(new PlantEntity());

        PlantDto plantDto = new PlantDto();

        mockMvc.perform(
                MockMvcRequestBuilders.multipart(PLANT_URL + "/create")
                        .file(new MockMultipartFile("plantDto", "", "application/json", TestUtils.asJsonString(plantDto).getBytes()))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreatePlant_Unauthorized() throws Exception {
        PlantDto plantDto = new PlantDto();

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart(PLANT_URL + "/create")
                                .file(new MockMultipartFile("plantDto", "", "application/json", TestUtils.asJsonString(plantDto).getBytes()))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user223", authorities = "USER")
    public void testCreatePlant_Forbidden() throws Exception {
        PlantDto plantDto = new PlantDto();

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart(PLANT_URL + "/create")
                                .file(new MockMultipartFile("plantDto", "", "application/json", TestUtils.asJsonString(plantDto).getBytes()))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
