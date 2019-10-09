package ar.edu.uade.integracion.shop.mock;

import ar.edu.uade.integracion.shop.entity.User;
import ar.edu.uade.integracion.shop.entity.UserDto;

public class UserMock {
    private UserMock(){}

    public static User createUser(){
        User userTest = new User();
        userTest.setId("1");
        userTest.setName("testu");
        return userTest;
    }

    public static UserDto createUserDtoFromUser(User seller) {
        return new UserDto(seller.getId(), seller.getName());
    }

}
