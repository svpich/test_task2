package app.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "group_id")
    private int groupId;

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

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "group_request",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "request_id")})
    private Set<CustomRequest> customRequestSet;

}
