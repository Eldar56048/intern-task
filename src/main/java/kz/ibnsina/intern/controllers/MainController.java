package kz.ibnsina.intern.controllers;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import kz.ibnsina.intern.dtos.request.RoomDtoRequest;
import kz.ibnsina.intern.dtos.response.RoomDtoResponse;
import kz.ibnsina.intern.exceptions.ConflictException;
import kz.ibnsina.intern.exceptions.NoAccessException;
import kz.ibnsina.intern.exceptions.ResourceNotFoundException;
import kz.ibnsina.intern.services.CountryService;
import kz.ibnsina.intern.services.RoomService;
import kz.ibnsina.intern.utils.facade.RoomFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class MainController {

    private final RoomService roomService;
    private final CountryService countryService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(RoomService roomService, CountryService countryService) {
        this.roomService = roomService;
        this.countryService = countryService;
    }


    @GetMapping
    public String main(Model model){
        model.addAttribute("rooms", roomService.getAll());
        model.addAttribute("isDevMode",  "dev".equals(profile));
        model.addAttribute("room", new RoomDtoRequest());
        model.addAttribute("countries", countryService.getAll());
        return "index";
    }

    @GetMapping("/rooms/{id}")
    public String get(@PathVariable Long id, HttpServletRequest request, Model model) throws IOException, GeoIp2Exception {
        if (!roomService.existsById(id))
            throw new ResourceNotFoundException("Комната №" + id + " не найдено", "room/not-found");
        if (!roomService.isIsoCodeEqualsToRoomsIsoCode(id, countryService.getIsoCode(/*request.getRemoteAddr()*/"79.141.162.81")))
            throw new NoAccessException("Вам запрещен вход в Комнату", "room/no-access");
        model.addAttribute("room", RoomFacade.roomToRoomDtoResponse(roomService.getById(id)));
        return "room-info";
    }

    @PostMapping("/rooms")
    public String addRoom(Model model, RoomDtoRequest dto) {
        if (dto.getName() == null) {
            throw new ConflictException("Данные формы неправильно заполнены", "room/not-filled");
        }
        if (dto.getIsoCode() == null) {
            throw new ConflictException("Данные формы неправильно заполнены", "room/not-filled");
        }
        roomService.add(dto);
        return "redirect:/";
    }

    /*@PostMapping("/rooms/{id}")*/
    @MessageMapping("/rooms/{id}")
    @SendTo("/topic/rooms")
    public RoomDtoResponse updateRoomsLight(@DestinationVariable Long id) throws IOException, GeoIp2Exception {
        if (!roomService.existsById(id))
            throw new ResourceNotFoundException("Комната №" + id + " не найдено", "room/not-found");
        if (!roomService.isIsoCodeEqualsToRoomsIsoCode(id, countryService.getIsoCode(/*request.getRemoteAddr()*/"79.141.162.81")))
            throw new NoAccessException("Вам запрещен вход в Комнату", "room/no-access");
        return RoomFacade.roomToRoomDtoResponse(roomService.changeLight(id));
    }
}
