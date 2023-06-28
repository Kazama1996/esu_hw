package com.esun.hw.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDto {
    private String userName;

    private String email;

    private String phone;

    private String password;

    private String biography;
}
