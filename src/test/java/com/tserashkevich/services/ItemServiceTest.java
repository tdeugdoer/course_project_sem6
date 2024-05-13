package com.tserashkevich.services;

import com.tserashkevich.models.Item;
import com.tserashkevich.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemService = new ItemService(itemRepository);
    }

    @Test
    void findAll_ShouldReturnListOfItems() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());

        when(itemRepository.findAll()).thenReturn(items);

        // Act
        List<Item> result = itemService.findAll();

        // Assert
        assertEquals(items, result);
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void findOne_ExistingId_ShouldReturnItem() {
        // Arrange
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        // Act
        Item result = itemService.findOne(itemId);

        // Assert
        assertEquals(item, result);
        verify(itemRepository, times(1)).findById(itemId);
    }

    @Test
    void findOne_NonExistingId_ShouldReturnNull() {
        // Arrange
        Long itemId = 1L;

        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act
        Item result = itemService.findOne(itemId);

        // Assert
        assertNull(result);
        verify(itemRepository, times(1)).findById(itemId);
    }

    @Test
    void save_ShouldSaveItemAndUploadFile() throws IOException {
        // Arrange
        Item item = new Item();
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test file".getBytes());

        //List<Item> listOngoingStubbing = when(itemRepository.findItemByPhotoName(item.getPhotoName())).thenReturn(Optional.empty());

        // Act
        itemService.save(item, file);

        // Assert
        verify(itemRepository, times(1)).save(item);
        verify(itemRepository, times(1)).findItemByPhotoName(item.getPhotoName());
        //verify(Files, times(1)).write(eq(Path.of("src/main/resources/static/photos/test.jpg")), any(byte[].class));
    }

    @Test
    void update_ShouldUpdateItemAndUploadNewFileAndDeleteOldFile() throws IOException {
        // Arrange
        Long itemId = 1L;
        Item oldItem = new Item();
        oldItem.setId(itemId);
        oldItem.setPhotoName("old.jpg");

        Item updatedItem = new Item();
        updatedItem.setPhotoName("new.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test file".getBytes());

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(oldItem));
        //when(itemRepository.findItemByPhotoName(updatedItem.getPhotoName())).thenReturn(Optional.empty());

        // Act
        itemService.update(itemId, updatedItem, file);

        // Assert
        verify(itemRepository, times(1)).save(updatedItem);
        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, times(1)).findItemByPhotoName(updatedItem.getPhotoName());
        verify(itemRepository, times(1)).findItemByPhotoName(oldItem.getPhotoName());
//        verify(Files, times(1)).write(eq(Path.of("src/main/resources/static/photos/new.jpg")), any(byte[].class));
//        verify(Files, times(1)).delete(eq(Path.of("src/main/resources/static/photos/old.jpg")));
    }
}
