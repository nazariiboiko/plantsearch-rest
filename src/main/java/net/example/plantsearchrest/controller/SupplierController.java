package net.example.plantsearchrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.mapper.SupplierMapper;
import net.example.plantsearchrest.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper = SupplierMapper.INSTANCE;
    @GetMapping
    public List<SupplierDto> getAllSuppliers(
            @RequestParam(value = "page", required = false, defaultValue ="0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size)
    {
        log.info("IN getAllSuppliers | return page {} in total {} objects", page, size);

        return supplierService.getAll(PageRequest.of(page, size)).stream()
                .map(supplierMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
