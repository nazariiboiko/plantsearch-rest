package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.entity.PlantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PlantMapperTest {

    private PlantMapper plantMapper = PlantMapper.INSTANCE;;

    private PlantEntity plantEntityExample;
    private PlantDto plantDtoExample;

    @BeforeEach
    public void setup() {
        plantEntityExample = new PlantEntity();
        plantDtoExample = new PlantDto();
        Random random = new Random();

        plantEntityExample.setId(42L);
        plantEntityExample.setName("RandomPlant");
        plantEntityExample.setLatinName("Randomus " + random.nextInt(100));
        plantEntityExample.setHeight(random.nextInt(100) + " cm");
        plantEntityExample.setHabitus("Random Habit");
        plantEntityExample.setGrowthRate("Random Growth Rate");
        plantEntityExample.setColor("Random Color");
        plantEntityExample.setSummerColor("Random Summer Color");
        plantEntityExample.setAutumnColor("Random Autumn Color");
        plantEntityExample.setFloweringColor("Random Flowering Color");
        plantEntityExample.setFrostResistance("Random Frost Resistance");
        plantEntityExample.setSketch("Random Sketch");
        plantEntityExample.setImage("Random Image URL");
        plantEntityExample.setRecommendation("Random Recommendation");
        plantEntityExample.setLighting("Random Lighting");
        plantEntityExample.setEvergreen("Random Evergreen");
        plantEntityExample.setFloweringPeriod("Random Flowering Period");
        plantEntityExample.setPlantType("Random Plant Type");
        plantEntityExample.setZoning("Random Zoning");
        plantEntityExample.setPh("Random pH");
        plantEntityExample.setSoilMoisture("Random Soil Moisture");
        plantEntityExample.setHardy("Random Hardy");
        plantEntityExample.setNutrition("Random Nutrition");

        plantDtoExample.setId(54L);
        plantDtoExample.setName("RandomPlant");
        plantDtoExample.setLatinName("Randomus " + random.nextInt(100));
        plantDtoExample.setHeight(random.nextInt(100) + " cm");
        plantDtoExample.setHabitus("Random Habit");
        plantDtoExample.setGrowthRate("Random Growth Rate");
        plantDtoExample.setColor("Random Color");
        plantDtoExample.setSummerColor("Random Summer Color");
        plantDtoExample.setAutumnColor("Random Autumn Color");
        plantDtoExample.setFloweringColor("Random Flowering Color");
        plantDtoExample.setFrostResistance("Random Frost Resistance");
        plantDtoExample.setSketch("Random Sketch");
        plantDtoExample.setImage("Random Image URL");
        plantDtoExample.setRecommendation("Random Recommendation");
        plantDtoExample.setLighting("Random Lighting");
        plantDtoExample.setEvergreen("Random Evergreen");
        plantDtoExample.setFloweringPeriod("Random Flowering Period");
        plantDtoExample.setPlantType("Random Plant Type");
        plantDtoExample.setZoning("Random Zoning");
        plantDtoExample.setPh("Random pH");
        plantDtoExample.setSoilMoisture("Random Soil Moisture");
        plantDtoExample.setHardy("Random Hardy");
        plantDtoExample.setNutrition("Random Nutrition");
    }

    @Test
    public void testCompareTo() {
        PlantDto plant1 = new PlantDto();
        plant1.setId(1L);

        PlantDto plant2 = new PlantDto();
        plant2.setId(2L);

        assertEquals(-1, plant1.compareTo(plant2));
        assertEquals(0, plant1.compareTo(plant1));
        assertEquals(1, plant2.compareTo(plant1));
    }

    @Test
    public void testEntityToDtoMapping() {
        PlantDto dto = plantMapper.mapEntityToDto(plantEntityExample);

        assertEquals(plantEntityExample.getId(), dto.getId());
        assertEquals(plantEntityExample.getName(), dto.getName());
        assertEquals(plantEntityExample.getLatinName(), dto.getLatinName());
        assertEquals(plantEntityExample.getHeight(), dto.getHeight());
        assertEquals(plantEntityExample.getHabitus(), dto.getHabitus());
        assertEquals(plantEntityExample.getGrowthRate(), dto.getGrowthRate());
        assertEquals(plantEntityExample.getColor(), dto.getColor());
        assertEquals(plantEntityExample.getSummerColor(), dto.getSummerColor());
        assertEquals(plantEntityExample.getAutumnColor(), dto.getAutumnColor());
        assertEquals(plantEntityExample.getFloweringColor(), dto.getFloweringColor());
        assertEquals(plantEntityExample.getFrostResistance(), dto.getFrostResistance());
        assertEquals(plantEntityExample.getSketch(), dto.getSketch());
        assertEquals(plantEntityExample.getImage(), dto.getImage());
        assertEquals(plantEntityExample.getRecommendation(), dto.getRecommendation());
        assertEquals(plantEntityExample.getLighting(), dto.getLighting());
        assertEquals(plantEntityExample.getEvergreen(), dto.getEvergreen());
        assertEquals(plantEntityExample.getFloweringPeriod(), dto.getFloweringPeriod());
        assertEquals(plantEntityExample.getPlantType(), dto.getPlantType());
        assertEquals(plantEntityExample.getZoning(), dto.getZoning());
        assertEquals(plantEntityExample.getPh(), dto.getPh());
        assertEquals(plantEntityExample.getSoilMoisture(), dto.getSoilMoisture());
        assertEquals(plantEntityExample.getHardy(), dto.getHardy());
        assertEquals(plantEntityExample.getNutrition(), dto.getNutrition());
    }

    @Test
    public void testDtoToEntityMapping() {
        PlantEntity entity = plantMapper.mapDtoToEntity(plantDtoExample);

        assertEquals(entity.getId(), plantDtoExample.getId());
        assertEquals(entity.getName(), plantDtoExample.getName());
        assertEquals(entity.getLatinName(), plantDtoExample.getLatinName());
        assertEquals(entity.getHeight(), plantDtoExample.getHeight());
        assertEquals(entity.getHabitus(), plantDtoExample.getHabitus());
        assertEquals(entity.getGrowthRate(), plantDtoExample.getGrowthRate());
        assertEquals(entity.getColor(), plantDtoExample.getColor());
        assertEquals(entity.getSummerColor(), plantDtoExample.getSummerColor());
        assertEquals(entity.getAutumnColor(), plantDtoExample.getAutumnColor());
        assertEquals(entity.getFloweringColor(), plantDtoExample.getFloweringColor());
        assertEquals(entity.getFrostResistance(), plantDtoExample.getFrostResistance());
        assertEquals(entity.getSketch(), plantDtoExample.getSketch());
        assertEquals(entity.getImage(), plantDtoExample.getImage());
        assertEquals(entity.getRecommendation(), plantDtoExample.getRecommendation());
        assertEquals(entity.getLighting(), plantDtoExample.getLighting());
        assertEquals(entity.getEvergreen(), plantDtoExample.getEvergreen());
        assertEquals(entity.getFloweringPeriod(), plantDtoExample.getFloweringPeriod());
        assertEquals(entity.getPlantType(), plantDtoExample.getPlantType());
        assertEquals(entity.getZoning(), plantDtoExample.getZoning());
        assertEquals(entity.getPh(), plantDtoExample.getPh());
        assertEquals(entity.getSoilMoisture(), plantDtoExample.getSoilMoisture());
        assertEquals(entity.getHardy(), plantDtoExample.getHardy());
        assertEquals(entity.getNutrition(), plantDtoExample.getNutrition());
    }

    @Test
    public void testEntityToPreviewDtoMapping() {
        PlantPreviewDto previewDto = plantMapper.mapEntityToPreviewDto(plantEntityExample);

        assertEquals(previewDto.getId(), plantEntityExample.getId());
        assertEquals(previewDto.getName(), plantEntityExample.getName());
        assertEquals(previewDto.getLatinName(), plantEntityExample.getLatinName());
        assertEquals(previewDto.getImage(), plantEntityExample.getImage());
        assertEquals(previewDto.getSketch(), plantEntityExample.getSketch());
    }

    @Test
    public void testUpdatePlantEntityMapping() {
        assertNotEquals(plantDtoExample.getId(), plantEntityExample.getId());

        plantMapper.updatePlantEntity(plantDtoExample, plantEntityExample);

        assertEquals(plantDtoExample.getId(), plantEntityExample.getId());
    }
}
