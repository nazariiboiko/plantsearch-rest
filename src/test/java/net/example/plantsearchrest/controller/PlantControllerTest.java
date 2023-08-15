package net.example.plantsearchrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.utils.PageUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantService plantService;

    @Autowired
    private PlantController plantController;

    @Test
    void test() throws Exception {

    }

    @Test
    void testGetPlantList() throws Exception {
        // Mock the behavior of the service layer
        Pageable pageable = PageRequest.of(0, 10);
        List<PlantDto> plantList = createListPlantDto(10);
        SinglePage<PlantDto> expectedResponse = PageUtil.create(plantList, pageable.getPageNumber(), pageable.getPageSize());

        when(plantService.getAll()).thenReturn(plantList);

        // Perform the request and validate the response
        mockMvc.perform(get("/api/v1/plants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].name").value("Plant 1"))
                .andExpect(jsonPath("$.data[1].name").value("Plant 2"))
                // Add more assertions for the response data
                .andExpect(jsonPath("$.pagination.totalItems").value(1))
                .andExpect(jsonPath("$.pagination.totalPages").value(1));
    }

    @Test
    void testGetPlantById_Success() throws Exception {
        List<PlantDto> list = createListPlantDto(20);
        PlantDto examp = list.get(0);
        when(plantService.getById(anyLong())).thenReturn(examp);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/plants/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String responseJson = response.getContentAsString();

        System.out.println("Response Status: " + response.getStatus());
        System.out.println("Response JSON: " + responseJson);
    }

    public List<PlantDto> createListPlantDto(int size) {
        List<PlantDto> plantList = new ArrayList<>();

        IntStream.range(0, size)
                .forEach((x) -> {
                        PlantDto e = new PlantDto(0L, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
                        e.setId((long) (Math.random() * 21235 % 100000 + 5000));
                        plantList.add(e);
                });
        return plantList;
    }
}
