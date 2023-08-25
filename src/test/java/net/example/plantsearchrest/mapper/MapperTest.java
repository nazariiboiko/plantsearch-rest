package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.entity.PlantEntity;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {


    @Test
    public void plantMapper_Test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        PlantMapper mapper = PlantMapper.INSTANCE;
        PlantEntity entity = new PlantEntity();
        String test = "test";
        entity.setId(-1L);
        entity.setName(test);
        entity.setLatinName(test);
        entity.setImage(test);
        entity.setSketch(test);

        PlantPreviewDto dto = mapper.mapEntityToPreviewDto(entity);

        Field[] fields = dto.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object dtoValue = field.get(dto);
            if(field.getName().equals("id"))
                assertEquals(dtoValue, -1L);
            else
                assertEquals(dtoValue, test);
        }
    }
}




