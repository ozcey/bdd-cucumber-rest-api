package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EndPoints {

    UserFindAll("/users/findAll"),
    UserFindById("/users/findById/{id}"),
    UserAdd("/users/insert"),
    UserUpdate("/users/update"),
    UserDelete("/users/delete/{id}"),
    CustomerFindAll("/customers/findAll"),
    CustomerFindById("/customers/id/{id}"),
    CustomerAdd("/customers/insert"),
    CustomerUpdate("/customers/update"),
    CustomerDelete("/customers/delete/{id}"),
    Login("/users/login");

    private final String endPoint;
}
