package org.jasr.dashard.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:sql.properties")
class PersistenceConfig {
	
	@Value("${OPENSHIFT_POSTGRESQL_DB_HOST}")
	private String host = "localhost";

	@Value("${OPENSHIFT_POSTGRESQL_DB_PORT}")
	private String port = "5432";
	
	@Value("${OPENSHIFT_POSTGRESQL_DB_USERNAME}")
	private String user = "postgres";
	
	@Value("${OPENSHIFT_POSTGRESQL_DB_PASSWORD}")
	private String pass = "root";
	
	private String db = "dashard";
	
    @Bean(initMethod = "migrate")
    Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:sql/");
        flyway.setDataSource(dataSource());
        flyway.migrate();
        return flyway;
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager() {
    	return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
    	return new JdbcTemplate(dataSource());
    }
    @Bean
    public DataSource dataSource() {
    	
    	String jdbcUrl = "jdbc:postgresql://" + host +":" + port +"/"+db;
    	
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(pass);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.setConnectionTestQuery("select * from devices");
        return new HikariDataSource(config);
    }

}
