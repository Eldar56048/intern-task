package kz.ibnsina.intern.controllers;

import kz.ibnsina.intern.services.CountryService;
import kz.ibnsina.intern.services.RoomService;
import kz.ibnsina.intern.utils.facade.RoomFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final CountryService countryService;

    @Autowired
    public RoomController(RoomService roomService, CountryService countryService) {
        this.roomService = roomService;
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(RoomFacade.roomListToRoomDtoResponseList(roomService.getAll()));
    }

    /*@PostMapping
    public ResponseEntity<?> add(@RequestBody RoomDtoRequest dto) {
        if (dto.getName() == null) {
            throw new ConflictException("Данные формы неправильно заполнены", "room/not-filled");
        }
        if (dto.getIsoCode() == null) {
            throw new ConflictException("Данные формы неправильно заполнены", "room/not-filled");
        }
        return ResponseEntity.ok(RoomFacade.roomToRoomDtoResponse(roomService.add(dto)));
    }*/

    /*@GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, HttpServletRequest request) throws IOException, GeoIp2Exception {
        if (!roomService.existsById(id))
            throw new ResourceNotFoundException("Комната №" + id + " не найдено", "room/not-found");
        if (!roomService.isIsoCodeEqualsToRoomsIsoCode(id, countryService.getIsoCode(*//*request.getRemoteAddr()*//*"79.141.162.81")))
            throw new NoAccessException("Вам запрещен вход в Комнату", "room/no-access");
        return ResponseEntity.ok(RoomFacade.roomToRoomDtoResponse(roomService.getById(id)));
    }*/

    /*@PostMapping("/{id}")
    public ResponseEntity<?> updateLight (@PathVariable Long id,RoomDtoRequest dto) throws IOException, GeoIp2Exception {
        if (!roomService.existsById(id))
            throw new ResourceNotFoundException("Комната №" + id + " не найдено", "room/not-found");
        if (!roomService.isIsoCodeEqualsToRoomsIsoCode(id, countryService.getIsoCode(*//*request.getRemoteAddr()*//*"79.141.162.81")))
            throw new NoAccessException("Вам запрещен вход в Комнату", "room/no-access");
        return ResponseEntity.ok(RoomFacade.roomToRoomDtoResponse(roomService.changeLight(id, dto.getLight())));
    }*/
}
