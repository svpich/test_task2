package app.converter;

import app.model.dto.CustomRequestDTO;
import app.model.entity.CustomRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomRequestMapper {

    CustomRequest customRequestDtoToCustomRequest(CustomRequestDTO customRequestDTO);
    CustomRequestDTO customRequestToCustomRequestDTO(CustomRequest customRequest);

}
