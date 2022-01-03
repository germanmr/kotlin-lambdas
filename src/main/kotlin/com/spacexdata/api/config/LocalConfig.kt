package com.spacexdata.api.config

import com.spacexdata.api.config.Profiles.LOCAL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
@Profile(LOCAL)
class LocalConfig {
    @Bean(name = ["datasource"])
    @Primary
    fun dataSource(): DataSource? {
        return DriverManagerDataSource().apply {
            this.setDriverClassName("org.postgresql.Driver")
            this.password = "postgres"
            this.username = "postgres"
            this.url = "jdbc:postgresql://localhost:5432/launchdb"
        }
    }
}