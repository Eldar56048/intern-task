package kz.ibnsina.intern.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDtoResponse {
    private Long id;
    private String name;
    private String isoCode;
    private String countryName;
    private Boolean light;
}
