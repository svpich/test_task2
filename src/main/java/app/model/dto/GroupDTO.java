package app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDTO {
    int id;
    String name;
    String screen_name;
    byte is_closed;
    String type;
    String photo_50;
    String photo_100;
    String photo_200;
}
