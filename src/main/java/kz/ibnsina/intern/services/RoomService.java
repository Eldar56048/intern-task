package kz.ibnsina.intern.services;

import kz.ibnsina.intern.dtos.request.RoomDtoRequest;
import kz.ibnsina.intern.models.Room;
import kz.ibnsina.intern.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> getAll() {
        return repository.findAll();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public Room getById(Long id) {
        return repository.findById(id).get();
    }

    public boolean isIsoCodeEqualsToRoomsIsoCode(Long id, String isoCode) {
        Room room = getById(id);
        return room.getIsoCode().equals(isoCode);
    }

    public Room add(RoomDtoRequest dto) {
        Room room = new Room(dto.getName(), dto.getIsoCode(), false);
        return save(room);
    }

    public Room save(Room room) {
        return repository.save(room);
    }

    public Room changeLight(Long id) {
        Room room = getById(id);
        room.setLight(!room.getLight());
        return save(room);
    }

}
