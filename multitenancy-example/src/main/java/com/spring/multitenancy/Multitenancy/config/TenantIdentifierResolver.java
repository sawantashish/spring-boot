package com.spring.multitenancy.Multitenancy.config;

import com.spring.multitenancy.Multitenancy.Constant;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getTenant();
        if (StringUtils.isEmpty(tenantId)) {
            tenantId = Constant.DEFAULT_SCHEMA;
        }
        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
