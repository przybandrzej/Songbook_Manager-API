package com.lazydev.stksongbook.webapp.restcontroller.unittests;

import com.lazydev.stksongbook.webapp.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.model.Author;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

public class AuthorDTOUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertAuthorEntityToAuthorDTO_thenCorrect() {
        Author author = new Author();
        author.setId(1L);
        author.setName(randomAlphabetic(6));

        AuthorDTO authorDto = modelMapper.map(author, AuthorDTO.class);
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getName(), authorDto.getName());
    }

    @Test
    public void whenConvertAuthorDTOToAuthorEntity_thenCorrect() {
        AuthorDTO authorDto = new AuthorDTO();
        authorDto.setId(1L);
        authorDto.setName(randomAlphabetic(6));

        Author author = modelMapper.map(authorDto, Author.class);
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
    }
}
