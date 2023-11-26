package site.nomoreparties.stellarburgers.helper;

import org.apache.commons.lang3.RandomStringUtils;
import site.nomoreparties.stellarburgers.model.RegisterUserRequest;
import site.nomoreparties.stellarburgers.model.UpdateUserRequest;

public class UserGenerator {
    public static RegisterUserRequest getRandomUser() {
        String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);

        return new RegisterUserRequest(email, password, name);
    }
    public static UpdateUserRequest getUpdateRandomUser(){
        String email = RandomStringUtils.randomAlphabetic(11) + "@yandex.ru";
        String name = RandomStringUtils.randomAlphabetic(11);
        return new UpdateUserRequest(email, name);
    }
}
