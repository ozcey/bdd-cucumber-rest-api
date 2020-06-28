package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EndPoints {

    UserFindAll("/user/findAll"),
    UserFindById("/user/findById"),
    UserAdd("/user/insert"),
    UserUpdate("/user/update"),
    UserDelete("/user/delete"),
    Login("/user/login");

    private final String endPoint;
}
