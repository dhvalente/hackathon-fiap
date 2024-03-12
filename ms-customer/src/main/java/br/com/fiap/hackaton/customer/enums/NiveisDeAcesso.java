package br.com.fiap.hackaton.customer.enums;

public enum NiveisDeAcesso {
    ADMIN("admin"),
    USER("user");

    private String role;

    NiveisDeAcesso(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
