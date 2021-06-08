package kz.ibnsina.intern.dtos.request;

import lombok.Data;

@Data
public class RoomDtoRequest {
    private Long id;
    private String name;
    private String isoCode;
    private Boolean light;
}
