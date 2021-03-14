package com.bookstore.bookstoremanager.users.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("Male"), FEMALE("Female");

    private String description;

}
