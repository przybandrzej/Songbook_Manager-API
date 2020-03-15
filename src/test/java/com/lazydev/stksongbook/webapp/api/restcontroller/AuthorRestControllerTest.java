package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.data.model.Author;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class AuthorRestControllerTest {

    @Test
    public void whenConvertAuthorEntityToAuthorDTO_thenCorrect() {
        Author author = new Author();
        author.setId(1L);
        author.setName("test");
        AuthorRestController restController = Mockito.mock(AuthorRestController.class);

        AuthorDTO authorDto = restController.convertToDto(author);

        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getName(), authorDto.getName());
    }

    @Test
    public void whenConvertAuthorDTOToAuthorEntity_thenCorrect() {
        AuthorDTO authorDto = new AuthorDTO();
        authorDto.setId(1L);
        authorDto.setName("test");
        AuthorRestController restController = Mockito.mock(AuthorRestController.class);

        Author author = restController.convertToEntity(authorDto);
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
    }
}