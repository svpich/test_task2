package app.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "group_vk")
public class Group {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "is_closed")
    private byte isClosed;

    @Column(name = "type")
    private String type;

    @Column(name = "photo_50")
    private String photo_50;

    @Column(name = "photo_100")
    private String photo_100;

    @Column(name = "photo_200")
    private String photo_200;

    @ManyToMany(mappedBy = "groupSet", fetch = FetchType.EAGER)
    private Set<CustomRequest> customRequestSet;

}
