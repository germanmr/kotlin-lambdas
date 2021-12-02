package com.german.notificationappkotlin.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("local")
class LocalConfig {
// TODO We can use this instead of taking the information from .properties files for example from a Secrets Server
//    @Bean(name = ["datasource"])
//    @Primary
//    fun dataSource(): DataSource? {
//        return DriverManagerDataSource().apply {
//            this.setDriverClassName("org.postgresql.Driver")
//            this.password = "postgres"
//            this.username = "postgres"
//            this.url = "jdbc:postgresql://localhost:5432/messages"
//        }
//    }
}