package com.esun.hw.dto.request;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class JwtPayload {
    private UUID userId;
    private Date expire;

}
