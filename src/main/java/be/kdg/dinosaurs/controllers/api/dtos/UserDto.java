package be.kdg.dinosaurs.controllers.api.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    private String username;
}
