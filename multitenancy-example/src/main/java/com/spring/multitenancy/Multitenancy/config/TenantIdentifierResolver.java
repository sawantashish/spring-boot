package com.spring.multitenancy.Multitenancy.config;

import com.spring.multitenancy.Multitenancy.Constant;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = Constant.DEFAULT_SCHEMA;
        if(tenantId != null){
            tenantId = TenantContext.getTenant();
        }
        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
