package kz.ibnsina.intern.webLayout;

import kz.ibnsina.intern.controllers.MainController;
import kz.ibnsina.intern.models.Room;
import kz.ibnsina.intern.repositories.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebLayoutTest {

    @Autowired
    private MainController controller;

    @Autowired
    private MockMvc mockMvc;

    /*@MockBean
    private RoomService service;*/
    @MockBean
    private RoomRepository repository;


    @Test
    public void contextLoads() throws Exception{
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/helloWorld")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void accessDeniedRoom() throws Exception {
        Room room = new Room(100L, "Room8", "UN", false);

        when(repository.existsById(100L)).thenReturn(true);
        when(repository.findById(100L)).thenReturn(Optional.of(room));

        this.mockMvc.perform(get("/rooms/100"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());

        verify(repository).existsById(100L);
        verify(repository).findById(100L);
    }

    @Test
    public void getRoomAccess() throws Exception {
        Room room = new Room(100L, "Room8", "US", false);

        when(repository.existsById(100L)).thenReturn(true);
        when(repository.findById(100L)).thenReturn(Optional.of(room));

        this.mockMvc.perform(get("/rooms/100"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(repository).existsById(100L);
        verify(repository, times(2)).findById(100L);
    }

    @Test
    public void addRoomSuccess() throws Exception {
        when(repository.save(any(Room.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        this.mockMvc.perform(post("/rooms")
                .param("name", "Room9")
                .param("isoCode", "US"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(repository).save(any(Room.class));
    }

    @Test
    public void addRoomWithError() throws Exception {
        this.mockMvc.perform(post("/rooms")
                .param("name", "Room9"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("CONFLICT")));
    }

    @Test
    public void roomNotFound() throws Exception {
        when(repository.existsById(100L)).thenReturn(false);

        this.mockMvc.perform(get("/rooms/100"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("NOT_FOUND")));

        verify(repository).existsById(100L);
    }

}