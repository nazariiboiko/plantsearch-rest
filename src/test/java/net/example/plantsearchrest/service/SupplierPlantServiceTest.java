package net.example.plantsearchrest.service;

import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.repository.SupplierPlantRepository;
import net.example.plantsearchrest.service.impl.SupplierPlantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierPlantServiceTest {

    @InjectMocks
    private SupplierPlantServiceImpl supplierPlantService;

    @Mock
    private SupplierPlantRepository rep;

    @Test
    void findByPlant__Success() {
        List<SupplierDto> dto = List.of(new SupplierDto());
        when(supplierPlantService.findByPlant(anyLong())).thenReturn(dto);

        supplierPlantService.findByPlant(1L);
    }
}
