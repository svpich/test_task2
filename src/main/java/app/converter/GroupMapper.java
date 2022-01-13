package app.converter;

import app.model.dto.GroupDTO;
import app.model.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupDTO groupToGroupDTO(Group group);

    Group groupDtoToGroup(GroupDTO groupDTO);

//    @Mapping(source = "groupDTO.customRequestDTOSet", target = "group.")
//    Set<Group> groupDtoSetToGroupSe2t(Set<GroupDTO> groupDTOSet);

}
