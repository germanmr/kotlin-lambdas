package com.spacexdata.api.config

import com.spacexdata.api.config.Profiles.PROD
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
@Profile(PROD)
class ProdConfig {
    @Bean(name = ["datasource"])
    @Primary
    fun dataSource(): DataSource? {
        return DriverManagerDataSource().apply {
            this.setDriverClassName("org.postgresql.Driver")
            this.password = "c9U9SVNus7LUuz7DL4V5"
            this.username = "postgres"
            this.url = "jdbc:postgresql://launchdb.cvk0ntd9uxln.us-east-2.rds.amazonaws.com:5432/launchdb"
        }
    }
}