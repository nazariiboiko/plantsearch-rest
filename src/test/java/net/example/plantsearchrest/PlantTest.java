package net.example.plantsearchrest;

import lombok.RequiredArgsConstructor;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.repository.PlantRepository;
import net.example.plantsearchrest.repository.UserRepository;
import net.example.plantsearchrest.service.PlantService;
import net.example.plantsearchrest.service.impl.PlantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlantTest {

    @Autowired
    private PlantRepository pRep;
    @Autowired
    private UserRepository uRep;

    @Test
    public void createUser() {
        UserEntity entity = new UserEntity();
        entity.setId(-9999L);
        entity.setLogin("testik1");
        entity.setPassword("22");
        entity.setEmail("ttt1");
        entity.setRole(Role.USER);
        entity.setStatus(Status.ACTIVE);

        uRep.save(entity);
    }

    @Test
    public void createPlant() {


    }
}
