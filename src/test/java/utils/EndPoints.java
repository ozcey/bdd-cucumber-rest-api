package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EndPoints {

    UserFindAll("/users/findAll"),
    UserFindById("/users/findById/{userId}"),
    UserAdd("/users/insert"),
    UserUpdate("/users/update"),
    UserDelete("/users/delete/{userId}"),
    Login("/users/login");

    private final String endPoint;
}
