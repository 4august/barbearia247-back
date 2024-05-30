package com.example.demo.domain;

public enum UsuarioRole {
    Admin("Admin"),
    Barbearia("Barbearia"),
    User("User");
    private String role;

    UsuarioRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
