package app.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "param_user_id")
    private String paramUserId;

    @Column(name = "param_substring")
    private String paramSubstring;

//    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "group_request",
            joinColumns = {@JoinColumn(name = "request_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<Group> groupSet;
}
