package app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class GroupDTO {
    int id;
    String name;
    String screenName;
    int isClosed;
    String type;
    String photo_50;
    String photo_100;
    String photo_200;


}
