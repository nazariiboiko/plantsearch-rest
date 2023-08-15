package net.example.plantsearchrest.controller;

import net.example.plantsearchrest.entity.Favourite;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.UserEntity;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.FavouriteService;
import net.example.plantsearchrest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
public class FavouriteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private FavouriteService favouriteService;

    @InjectMocks
    private FavouriteController favouriteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favouriteController).build();
    }

    @Test
    @WithMockUser
    void testGetFavouritesByAccount_Success() throws Exception {
        JwtUser user = new JwtUser(1L, "admin", "a", "2", true, List.of());

        List<Favourite> favourites = new ArrayList<>(List.of(new Favourite(new PlantEntity(), new UserEntity()), new Favourite(new PlantEntity(), new UserEntity())));
        UserEntity userEntity = new UserEntity();
        userEntity.setFavourites(favourites);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userService.findById(user.getId())).thenReturn(userEntity);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/favourites/my"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(favourites.size()))
                .andReturn();
    }

    @Test
    void testChangeFavouriteStatement_Success() throws Exception {
        JwtUser user = new JwtUser(1L, "admin", "a", "2", true, List.of());
        UserEntity userEntity = new UserEntity();
        Long plantId = 1L;

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        doNothing().when(favouriteService).changeLikeStatement(plantId, user.getId());

        mockMvc.perform(post("/api/v1/favourites/like")
                        .param("id", String.valueOf(plantId)))
                .andExpect(status().isOk())
                .andReturn();

        verify(favouriteService, times(1)).changeLikeStatement(plantId, user.getId());
    }

}
