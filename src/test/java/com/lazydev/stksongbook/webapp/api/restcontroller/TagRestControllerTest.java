package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.TagDTO;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class TagRestControllerTest {

    @Test
    public void whenConvertTagEntityToTagDTO_thenCorrect() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("test");
        TagRestController restController = Mockito.mock(TagRestController.class);

        TagDTO tagDto = restController.convertToDto(tag);

        assertEquals(tag.getId(), tagDto.getId());
        assertEquals(tag.getName(), tagDto.getName());
    }

    @Test
    public void whenConvertTagDTOToTagEntity_thenCorrect() {
        TagDTO tagDto = new TagDTO();
        tagDto.setId(1L);
        tagDto.setName("test");
        TagRestController restController = Mockito.mock(TagRestController.class);

        Tag tag = restController.convertToEntity(tagDto);
        assertEquals(tagDto.getId(), tag.getId());
        assertEquals(tagDto.getName(), tag.getName());
    }
}