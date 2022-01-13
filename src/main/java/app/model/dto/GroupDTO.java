package app.model.dto;

import app.model.entity.CustomRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class GroupDTO {
    private int id;
    private String name;
    private String screenName;
    private int isClosed;
    private String type;
    private String photo_50;
    private String photo_100;
    private String photo_200;
//    private Set<CustomRequest> customRequestSet;
}
