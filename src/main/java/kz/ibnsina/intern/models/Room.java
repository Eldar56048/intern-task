package kz.ibnsina.intern.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String isoCode;
    private Boolean light;

    public Room(String name, String isoCode, Boolean light) {
        this.name = name;
        this.isoCode = isoCode;
        this.light = light;
    }
}
