package kz.ibnsina.intern.utils.facade;

import kz.ibnsina.intern.dtos.response.RoomDtoResponse;
import kz.ibnsina.intern.models.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomFacade {
    public static RoomDtoResponse roomToRoomDtoResponse(Room room) {
        RoomDtoResponse dto = new RoomDtoResponse();
        if(room.getId()!=null) {
            dto.setId(room.getId());
        }
        if(room.getName()!=null) {
            dto.setName(room.getName());
        }
        if(room.getLight()!=null) {
            dto.setLight(room.getLight());
        }
        if(room.getIsoCode()!=null) {
            dto.setIsoCode(room.getIsoCode());
            Locale l = new Locale("", room.getIsoCode());
            dto.setCountryName(l.getDisplayCountry());
        }
        return dto;
    }

    public static List<RoomDtoResponse> roomListToRoomDtoResponseList(List<Room> rooms) {
        List<RoomDtoResponse> dtoList = new ArrayList<>();
        for (Room room : rooms) {
            dtoList.add(roomToRoomDtoResponse(room));
        }
        return dtoList;
    }
}
