package com.lemnos.server.models.enums;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN("ADMIN"),
    FUNCIONARIO("FUNCIONARIO"),
    CLIENTE("CLIENTE"),
    USUARIO("USUARIO");

    private final String role;

    Roles(String role) {
        this.role = role;
    }
}
