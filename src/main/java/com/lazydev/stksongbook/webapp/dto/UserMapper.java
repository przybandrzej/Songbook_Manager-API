package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User entity);

    User userDTOToUser(UserDTO dto);
}
