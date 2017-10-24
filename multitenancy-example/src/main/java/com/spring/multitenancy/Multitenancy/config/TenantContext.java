package com.spring.multitenancy.Multitenancy.config;

public class TenantContext {

    private static ThreadLocal<String> tenant = new ThreadLocal<>();

    public static void setTenant(String tenantId){
        tenant.set(tenantId);
    }

    public static String getTenant(){
        return tenant.get();
    }

    public static void clear(){
        tenant.set(null);
    }
}