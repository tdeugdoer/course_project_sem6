package com.tserashkevich.util;

import com.tserashkevich.models.Item;
import com.tserashkevich.models.User;
import com.tserashkevich.services.ItemService;
import com.tserashkevich.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ItemValidator implements Validator {
    private final ItemService itemService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Item.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Item newItem = (Item) o;
        Optional<Item> findItem = itemService.findByName(newItem.getName());

        if (findItem.isPresent() && !Objects.equals(newItem.getId(), findItem.get().getId()))
            errors.rejectValue("name", "",
                    "Товар с таким названием уже имеется. Поменяйте название нового товара");
    }
}
