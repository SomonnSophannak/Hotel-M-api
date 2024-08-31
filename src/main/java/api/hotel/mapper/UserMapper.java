package api.hotel.mapper;


import api.hotel.domain.User;
import api.hotel.features.auth.dto.RegisterRequest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromRegisterRequest(RegisterRequest registerRequest);

}
