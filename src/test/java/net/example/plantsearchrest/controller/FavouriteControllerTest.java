package net.example.plantsearchrest.controller;

import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.exception.NotFoundException;
import net.example.plantsearchrest.security.jwt.JwtTokenProvider;
import net.example.plantsearchrest.security.jwt.JwtUser;
import net.example.plantsearchrest.service.FavouriteService;
import net.example.plantsearchrest.service.UserService;
import net.example.plantsearchrest.utils.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("dev")
@WebMvcTest(FavouriteController.class)
public class FavouriteControllerTest {

    private String FAV_URL = "http://localhost:8085/api/v1/favourites";
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private FavouriteService favouriteService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private Messages messages;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "testuser", authorities = "ADMIN")
    public void testGetFavouritesByUserId_Success() throws Exception {
        List<PlantPreviewDto> favourites = Collections.singletonList(new PlantPreviewDto());

        when(userService.getFavouritesByUserId(anyLong())).thenReturn(favourites);

        mockMvc.perform(MockMvcRequestBuilders
                    .get(FAV_URL + "/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = "USER")
    public void testGetFavouritesByUserId_Denied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(FAV_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = "ADMIN")
    public void testGetFavouritesByUserId_Failure() throws Exception {

        when(userService.getFavouritesByUserId(anyLong())).thenThrow(new NotFoundException("Not found", "NOT_FOUND"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(FAV_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFavouritesByAccount_Success() throws Exception {
        JwtUser user = new JwtUser(1L, "username", "password", "", true, List.of(new SimpleGrantedAuthority("ADMIN")));

        when(userService.getPrincipal()).thenReturn(user);
        when(userService.getFavouritesByUserId(anyLong())).thenReturn(Collections.singletonList(new PlantPreviewDto()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(FAV_URL + "/my")
                        .with(user("username").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    public void getFavouritesByAccount_Failure() throws Exception {
        JwtUser user = new JwtUser(1L, "username", "password", "", true, List.of(new SimpleGrantedAuthority("ADMIN")));

        when(userService.getPrincipal()).thenReturn(user);
        when(userService.getFavouritesByUserId(anyLong())).thenThrow(new NotFoundException("User not found","USER_NOT_FOUND"));

        mockMvc.perform(get(FAV_URL + "/my")
                        .with(user("username").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFavouritesByAccount_Denied() throws Exception {
        mockMvc.perform(get(FAV_URL + "/my")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    public void changeFavouriteStatement_Success() throws Exception {
        when(userService.getPrincipal()).thenReturn(new JwtUser(1L,"","","",true, List.of()));

        mockMvc.perform(post(FAV_URL + "/like")
                        .with(user("username").roles("ADMIN"))
                        .param("id","1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void changeFavouriteStatement_Failure() throws Exception {
        when(userService.getPrincipal()).thenReturn(new JwtUser(1L,"","","",true, List.of()));

        mockMvc.perform(post(FAV_URL + "/like")
                        .with(user("username").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void changeFavouriteStatement_Denied() throws Exception {
        when(userService.getPrincipal()).thenReturn(new JwtUser(1L,"","","",true, List.of()));

        mockMvc.perform(post(FAV_URL + "/like")
                        .param("id","1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
