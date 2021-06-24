package kz.ibnsina.intern.room;

import kz.ibnsina.intern.dtos.request.RoomDtoRequest;
import kz.ibnsina.intern.models.Room;
import kz.ibnsina.intern.repositories.RoomRepository;
import kz.ibnsina.intern.services.RoomService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

    //data's for testing
    private static Room room1;
    private static Room room2;
    private static List<Room> roomList;

    @InjectMocks
    RoomService service;

    @Mock
    RoomRepository repository;

    @BeforeClass
    public static void initDataForTesting() {
        room1 = new Room(1L, "Room1", "KZ", false);
        room2 = new Room(2L,"Room2", "US", true);
        roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
    }

    @Test
    @DisplayName("Test getById success")
    public void getByIdTest() {
        Room expectedRoom = room1;
        when(repository.findById(1L)).thenReturn(Optional.of(expectedRoom));
        Room actualRoom = service.getById(1L);
        verify(repository).findById(1L);
        Assert.assertNotNull("Get by id test failed because actual data is null",actualRoom);
        Assert.assertEquals("Get by id test failed because expected data is "+expectedRoom.getName()+" but actual is"+actualRoom.getName(), expectedRoom, actualRoom);
    }

    @Test
    @DisplayName("Test existsById success")
    public void existsByIdSuccessTest() {
        when(repository.existsById(1L)).thenReturn(true);
        Boolean expected = true;
        Boolean actual = service.existsById(1L);
        verify(repository).existsById(1L);
        Assert.assertEquals("Exists by id test failed because expected "+expected.toString()+" actual "+actual.toString(), expected ,actual);
    }

    @Test
    @DisplayName("Test existsById fail")
    public void existsByIdFailTest() {
        when(repository.existsById(3L)).thenReturn(false);
        Boolean expected = false;
        Boolean actual = service.existsById(3L);
        verify(repository).existsById(3L);
        Assert.assertEquals("Exists by id test failed because expected "+expected.toString()+" actual "+actual.toString(), expected ,actual);
    }

    @Test
    @DisplayName("Test getAll success")
    public void getAllTest() {
        when(repository.findAll()).thenReturn(roomList);
        List<Room> expectedRooms = roomList;
        List<Room> actualRooms = service.getAll();
        verify(repository).findAll();
        Assert.assertEquals("Exists by id test failed because expected length "+expectedRooms.size()+" actual length"+actualRooms.size(), expectedRooms.size() ,actualRooms.size());
    }

    @Test
    @DisplayName("Test isIsoCodeEqualsToRoomsIsoCode success")
    public void isIsoCodeEqualsToRoomsIsoCodeSuccessTest(){
        when(repository.findById(1L)).thenReturn(Optional.of(room1));
        Boolean expected = true;
        Boolean actual = service.isIsoCodeEqualsToRoomsIsoCode(1L, "KZ");
        verify(repository).findById(1L);
        Assert.assertEquals("Is isoCode equals to Rooms Iso code success  test failed because expected "+expected.toString()+" actual "+actual.toString(), expected ,actual);
    }

    @Test
    @DisplayName("Test isIsoCodeEqualsToRoomsIsoCode fail")
    public void isIsoCodeEqualsToRoomsIsoCodeFailTest(){
        when(repository.findById(1L)).thenReturn(Optional.of(room1));
        Boolean expected = false;
        Boolean actual = service.isIsoCodeEqualsToRoomsIsoCode(1L, "US");
        verify(repository).findById(1L);
        Assert.assertEquals("Is isoCode equals to Rooms Iso code fail  test failed because expected "+expected.toString()+" actual "+actual.toString(), expected ,actual);
    }

    @Test
    @DisplayName("Test changeLight success")
    public void changeLightTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(room1));
        when(repository.save(any(Room.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });
        Boolean expected = true;
        Boolean actual = service.changeLight(1L).getLight();
        verify(repository).findById(1L);
        verify(repository).save(any(Room.class));
        Assert.assertEquals("Change light test failed because expected "+expected.toString()+" actual "+actual.toString(), expected ,actual);
    }

    @Test
    @DisplayName("Test addRoom")
    public void addRoomTest() {
        RoomDtoRequest roomDtoRequest = new RoomDtoRequest();
        roomDtoRequest.setName("Room3");
        roomDtoRequest.setIsoCode("KZ");

        when(repository.save(any(Room.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        Room actualRoom = service.add(roomDtoRequest);
        verify(repository).save(any(Room.class));
        Assert.assertEquals("Add room test failed because expected roomName"+roomDtoRequest.getName()+" actual name "+actualRoom.getName(),roomDtoRequest.getName(), actualRoom.getName());
        Assert.assertEquals("Add room test failed because expected isoCode"+roomDtoRequest.getIsoCode()+" actual isoCode "+actualRoom.getIsoCode(),roomDtoRequest.getIsoCode(), actualRoom.getIsoCode());
    }

    @Test
    @DisplayName("Test save")
    public void saveTest() {
        Room expectedRoom = room1;
        when(repository.save(any(Room.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });
        Room actualRoom = service.save(expectedRoom);
        verify(repository).save(any(Room.class));
        Assert.assertEquals("Save test failed because expected "+expectedRoom+" actual "+actualRoom ,expectedRoom, actualRoom);
    }

}
