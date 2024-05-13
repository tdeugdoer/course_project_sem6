package com.tserashkevich.services;

import com.tserashkevich.models.Item;
import com.tserashkevich.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final String basePath = "src/main/resources/static/photos/";


    @Transactional(readOnly = true)
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Item findOne(Long id) {
        Optional<Item> foundProduct = itemRepository.findById(id);
        return foundProduct.orElse(null);
    }

    public void save(Item item, MultipartFile file) {
        item.setPhotoName(file.getOriginalFilename());
        try {
            if (itemRepository.findItemByPhotoName(item.getPhotoName()).isEmpty())
                Files.write(Path.of(basePath + item.getPhotoName()), file.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }
        itemRepository.save(item);
    }

    public void update(Long id, Item updatedItem, MultipartFile file) {
        updatedItem.setId(id);
        updatedItem.setPhotoName(file.getOriginalFilename());
        Item oldItem = itemRepository.findById(id).get();
        try {
            if (!Objects.equals(updatedItem.getPhotoName(), oldItem.getPhotoName())) {
                if (itemRepository.findItemByPhotoName(updatedItem.getPhotoName()).isEmpty())
                    Files.write(Path.of(basePath + updatedItem.getPhotoName()), file.getBytes());
                if (itemRepository.findItemByPhotoName(oldItem.getPhotoName()).size() == 1)
                    Files.delete(Path.of(basePath + oldItem.getPhotoName()));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }
        itemRepository.save(updatedItem);
    }

    public void delete(Long id) {
        Item deletedItem = itemRepository.findById(id).get();
        if (itemRepository.findItemByPhotoName(deletedItem.getPhotoName()).size() == 1) {
            try {
                Files.delete(Path.of(basePath + deletedItem.getPhotoName()));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        itemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Item> findByName(String name) {
        return itemRepository.findItemByName(name);
    }
}
