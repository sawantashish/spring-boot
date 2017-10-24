package com.spring.multitenancy.Multitenancy.dao;

import com.spring.multitenancy.Multitenancy.entity.Client;
import org.springframework.stereotype.Repository;

@Repository("iClientDAO")
public class ClientDAO extends GenericDAO<Client,String> {

    public ClientDAO() {
        super(Client.class);
    }
}
