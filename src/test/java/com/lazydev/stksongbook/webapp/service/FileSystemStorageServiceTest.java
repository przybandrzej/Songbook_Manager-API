package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.config.StorageProperties;
import com.lazydev.stksongbook.webapp.service.exception.FileNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
class FileSystemStorageServiceTest {

  private FileSystemStorageService storageService;

  @BeforeEach
  void setUp() {
    StorageProperties properties = Mockito.mock(StorageProperties.class);
    given(properties.getLocation()).willReturn("./test_storage");
    storageService = new FileSystemStorageService(properties);
  }

  @Test
  public void loadAsResource() {
    assertThrows(FileNotFoundException.class, () -> storageService.loadAsResource("wrong"));
    assertThrows(FileNotFoundException.class, () -> storageService.loadAsResource("fdds.fds.fds"));
    assertThrows(FileNotFoundException.class, () -> storageService.loadAsResource(""));
    assertThrows(FileNotFoundException.class, () -> storageService.loadAsResource(null));
    assertDoesNotThrow(() -> storageService.loadAsResource("test_file.txt"));
    assertTrue(storageService.loadAsResource("test_file.txt").exists());
  }

  @Test
  public void loadAll() {
    assertEquals(1, storageService.loadAll().count());
    assertTrue(storageService.loadAll().findFirst().isPresent());
    assertEquals("test_file.txt", storageService.loadAll().findFirst().get().getFileName().toString());
  }

  @Test
  public void store() throws Exception {
    final InputStream inputStream = storageService.loadAsResource("test_file.txt").getInputStream();
    final InputStream inputStream2 = storageService.loadAsResource("test_file.txt").getInputStream();
    final MockMultipartFile file1 = new MockMultipartFile("file", "test_file.txt", "text/plain", inputStream);
    final MockMultipartFile file2 = new MockMultipartFile("file", "test_file..txt", "text/plain", inputStream2);
    final MockMultipartFile file3 = new MockMultipartFile("file", "test_file3.txt", "text/plain", "".getBytes());

    assertAll(() -> assertDoesNotThrow(() -> storageService.store(file1)),
        () -> assertEquals("test_file.txt", storageService.store(file1)));
    assertThrows(StorageException.class, () -> storageService.store(file2));
    assertThrows(StorageException.class, () -> storageService.store(file3));

  }
}
