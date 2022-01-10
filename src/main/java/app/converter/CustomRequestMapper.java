package app.converter;

import app.model.dto.CustomRequestDTO;
import app.model.entity.CustomRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomRequestMapper {

    CustomRequest customRequestDtoToCustomRequest(CustomRequestDTO customRequestDTO);
    CustomRequestDTO customRequestToCustomRequestDTO(CustomRequest customRequest);

}
