package com.esun.hw.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private UUID userId;

    private String userName;

    private String email;

    private String phone;

    private String password;

    private String biography;
}
