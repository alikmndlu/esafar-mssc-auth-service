package com.alikmndlu.authservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String id;
    private String name;
    private String emailAddress;
    private String password;
}