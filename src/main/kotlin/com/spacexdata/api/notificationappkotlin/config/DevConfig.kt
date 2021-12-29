package com.spacexdata.api.notificationappkotlin.config

import com.spacexdata.api.notificationappkotlin.config.Profiles.DEV
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
@Profile(DEV)
class DevConfig {
    @Bean(name = ["datasource"])
    @Primary
    fun dataSource(): DataSource? {
        return DriverManagerDataSource().apply {
            this.setDriverClassName("org.postgresql.Driver")
            this.password = "somepassword"
            this.username = "postgres"
            this.url = "jdbc:postgresql://somedatabasename.amazonurl:5432/somedatabasename"
        }
    }
}