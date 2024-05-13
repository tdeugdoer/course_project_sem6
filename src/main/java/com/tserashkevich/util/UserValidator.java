package com.tserashkevich.util;

import com.tserashkevich.models.User;
import com.tserashkevich.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (userService.findUserByLogin(user.getLogin()).isPresent())
            errors.rejectValue("login", "", "Аккаунт с таким login уже существует");
        if (userService.getUserByPhoneNumber(user.getPhoneNumber()).isPresent())
            errors.rejectValue("phoneNumber", "", "Аккаунт с таким номером телефона уже существует");
    }
}
