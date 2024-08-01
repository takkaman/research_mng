package com.example.research_mng.entity;

import lombok.Data;

@Data
public class LoginResult {
    private Long userId;
    private String userName;
    private int userType;
}