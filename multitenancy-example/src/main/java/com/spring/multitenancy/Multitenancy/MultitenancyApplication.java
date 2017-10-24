package com.spring.multitenancy.Multitenancy;

import com.spring.multitenancy.Multitenancy.config.TenantContextInterceptor;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@SpringBootApplication
public class MultitenancyApplication extends WebMvcConfigurerAdapter{

	@Autowired
	TenantContextInterceptor requestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor);
	}

	public static void main(String[] args) {
		SpringApplication.run(MultitenancyApplication.class, args);
	}

	@Bean
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder
				.create()
				.username("root")
				.password("root")
				.url("jdbc:mysql://localhost:3306/common")
				.driverClassName("com.mysql.jdbc.Driver")
				.build();
	}

	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){
		return hemf.getSessionFactory();
	}
}
