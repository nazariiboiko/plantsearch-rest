package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.SupplierEntity;
import net.example.plantsearchrest.model.SinglePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierMapperTest {
    private SupplierMapper supplierMapper = SupplierMapper.INSTANCE;

    private SupplierEntity supplierEntityExample;
    private SupplierDto supplierDtoExample;

    @BeforeEach
    public void setup() {
        PlantEntity plantEntity = new PlantEntity();
        plantEntity.setId(1L);

        supplierEntityExample = new SupplierEntity();
        supplierEntityExample.setId(1L);
        supplierEntityExample.setName("Random Name Corp.");
        supplierEntityExample.setAvaliablePlants(List.of(plantEntity));

        supplierDtoExample = new SupplierDto().toBuilder()
                .id(2L)
                .name("Random Name Inc.")
                .avaliablePlants(new SinglePage<>())
                .build();
    }

    @Test
    public void testComparableTo() {
        SupplierDto dto1 = new SupplierDto().toBuilder().id(11L).build();
        SupplierDto dto2 = new SupplierDto().toBuilder().id(25L).build();

        assertEquals(-1, dto1.compareTo(dto2));
        assertEquals(0, dto1.compareTo(dto1));
        assertEquals(1, dto2.compareTo(dto1));
    }

    @Test
    public void testEntityToDtoMapping() {
        SupplierDto dto = supplierMapper.mapEntityToDto(supplierEntityExample, PageRequest.of(1, 20));

        assertEquals(dto.getId(), supplierEntityExample.getId());
        assertEquals(dto.getName(), supplierEntityExample.getName());
        assertNotNull(dto.getAvaliablePlants());
    }

    @Test
    public void testEntityToDtoIgnorePlantMapping() {
        SupplierDto dto = supplierMapper.mapEntityToDtoIgnorePlants(supplierEntityExample);

        assertEquals(dto.getId(), supplierEntityExample.getId());
        assertEquals(dto.getName(), supplierEntityExample.getName());
        assertNull(dto.getAvaliablePlants());
    }

    @Test
    public void testUpdateEntityMapping() {
        assertNotEquals(supplierDtoExample.getId(), supplierEntityExample.getId());
        supplierMapper.updateSupplierEntity(supplierDtoExample, supplierEntityExample);
        assertEquals(supplierEntityExample.getId(), supplierDtoExample.getId());
    }
}
