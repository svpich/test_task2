package app.converter;

import app.model.dto.UserDTO;
import app.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
}