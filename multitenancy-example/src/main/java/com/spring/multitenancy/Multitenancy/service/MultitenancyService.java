package com.spring.multitenancy.Multitenancy.service;

import com.spring.multitenancy.Multitenancy.dao.ClientDAO;
import com.spring.multitenancy.Multitenancy.dto.ClientDTO;
import com.spring.multitenancy.Multitenancy.entity.Client;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("multitenancyService")
public class MultitenancyService {

    @Autowired
    ClientDAO clientDAO;

    @Transactional
    public ClientDTO getClientInfo() {
        Client client =(Client) clientDAO.getCriteria(Client.class).list().get(0);
        return new ClientDTO(client.getClientId(), client.getDescription());
    }
}
