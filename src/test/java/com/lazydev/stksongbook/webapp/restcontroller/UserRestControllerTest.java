package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.UserDTO;
import com.lazydev.stksongbook.webapp.model.User;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class UserRestControllerTest {

    /*@Test
    public void whenConvertUserEntityToUserDTO_thenCorrect() {
        User user = new User();
        user.setId(1L);
        user.setName("test");
        UserRestController restController = Mockito.mock(UserRestController.class);

        UserDTO userDto = restController.convertToDto(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
    }

    @Test
    public void whenConvertUserDTOToUserEntity_thenCorrect() {
        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        userDto.setName("test");
        UserRestController restController = Mockito.mock(UserRestController.class);

        User user = restController.convertToEntity(userDto);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getName(), user.getName());
    }*/
}