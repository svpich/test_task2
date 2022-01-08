package app.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_vk")
public class Group {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "screen_name")
    String screenName;

    @Column(name = "is_closed")
    byte isClosed;

    @Column(name = "type")
    String type;

    @Column(name = "photo_50")
    String photo_50;

    @Column(name = "photo_100")
    String photo_100;

    @Column(name = "photo_200")
    String photo_200;
}
