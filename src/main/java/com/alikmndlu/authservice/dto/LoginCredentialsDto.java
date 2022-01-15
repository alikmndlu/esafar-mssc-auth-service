package com.alikmndlu.authservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginCredentialsDto {

    private String emailAddress;
    private String password;
}
