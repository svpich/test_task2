package app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomRequestDTO {

    private int id;
    private LocalDate date;
    private LocalTime time;
    private String paramUserId;
    private String paramSubstring;
    private Set<GroupDTO> groupSet;
}
