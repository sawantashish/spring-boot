package com.spring.multitenancy.Multitenancy.dto;

public class ClientDTO {

    private String clientId;
    private String description;

    public ClientDTO(String clientId, String description){
        this.clientId = clientId;
        this.description = description;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
