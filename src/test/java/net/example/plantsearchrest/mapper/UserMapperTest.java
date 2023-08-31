package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.Role;
import net.example.plantsearchrest.entity.Status;
import net.example.plantsearchrest.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserMapperTest {

    private UserMapper userMapper = UserMapper.INSTANCE;
    private UserEntity userEntityExample;
    private UserDto userDtoExample;

    @BeforeEach
    public void setup() {
        userEntityExample = new UserEntity();
        userDtoExample = new UserDto();
        Random random = new Random();

        userEntityExample.setId(99L);
        userEntityExample.setEmail("random.email@random.com");
        userEntityExample.setLogin("randomuser" + random.nextInt(100));
        userEntityExample.setPassword("rAnDoMh4sHpAsCw0rD");
        userEntityExample.setRole(Role.USER);
        userEntityExample.setStatus(Status.ACTIVE);
        userEntityExample.setRegistrationDate(LocalDate.now());
        userEntityExample.setLastLogin(LocalDateTime.now());

        userDtoExample.setId(98L);
        userDtoExample.setEmail("random.email@random.com");
        userDtoExample.setLogin("randomuser" + random.nextInt(100));
        userDtoExample.setPassword("rAnDoMh4sHpAsCw0rD");
        userDtoExample.setRole(Role.USER);
        userDtoExample.setStatus(Status.ACTIVE);
        userDtoExample.setRegistrationDate(LocalDate.now());
        userDtoExample.setLastLogin(LocalDateTime.now());
    }

    @Test
    public void testCompareTo() {
        UserDto dto1 = new UserDto().toBuilder().id(11L).build();
        UserDto dto2 = new UserDto().toBuilder().id(25L).build();

        assertEquals(-1, dto1.compareTo(dto2));
        assertEquals(0, dto1.compareTo(dto1));
        assertEquals(1, dto2.compareTo(dto1));
    }

    @Test
    public void testEntityToDtoMapping() {
        UserDto dto = userMapper.mapEntityToDto(userEntityExample);

        assertEquals(dto.getId(), userEntityExample.getId());
        assertEquals(dto.getEmail(), userEntityExample.getEmail());
        assertEquals(dto.getLogin(), userEntityExample.getLogin());
        assertEquals(dto.getPassword(), userEntityExample.getPassword());
        assertEquals(dto.getRole(), userEntityExample.getRole());
        assertEquals(dto.getStatus(), userEntityExample.getStatus());
        assertEquals(dto.getRegistrationDate(), userEntityExample.getRegistrationDate());
        assertEquals(dto.getLastLogin(), userEntityExample.getLastLogin());
    }

    @Test
    public void testDtoToEntityMapping() {
        UserEntity entity = userMapper.mapDtoToEntity(userDtoExample);

        assertEquals(entity.getId(), userDtoExample.getId());
        assertEquals(entity.getEmail(), userDtoExample.getEmail());
        assertEquals(entity.getLogin(), userDtoExample.getLogin());
        assertEquals(entity.getPassword(), userDtoExample.getPassword());
        assertEquals(entity.getRole(), userDtoExample.getRole());
        assertEquals(entity.getStatus(), userDtoExample.getStatus());
        assertEquals(entity.getRegistrationDate(), userDtoExample.getRegistrationDate());
        assertEquals(entity.getLastLogin(), userDtoExample.getLastLogin());
    }

    @Test
    public void testUpdateEntityMapping() {
        assertNotEquals(userDtoExample.getId(), userEntityExample.getId());
        userMapper.updateUserEntity(userDtoExample, userEntityExample);
        assertEquals(userDtoExample.getId(), userEntityExample.getId());
    }
}
