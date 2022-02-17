package app.general.common.datasource;



import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DataSourceConfig {

    @Primary
    @Bean("readWriteDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties readWriteDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("readOnlyDataSourceProperties")
    @ConfigurationProperties("spring.read-only-datasource")
    public DataSourceProperties readOnlyDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("readWriteDataSource")
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource readWriteDataSource(@Qualifier("readWriteDataSourceProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("readOnlyDataSource")
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource readOnlyDataSource(@Qualifier("readOnlyDataSourceProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    // =================================================================================================================

    @DependsOn({"readWriteDataSource", "readOnlyDataSource"})
    @Primary
    @Bean
    public DataSource primaryDataSource(@Qualifier("readWriteDataSource") DataSource readWriteDataSource, @Qualifier("readOnlyDataSource") DataSource readOnlyDataSource){
        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(RoutingDataSource.Route.PRIMARY, readWriteDataSource);
        targetDataSources.put(RoutingDataSource.Route.REPLICA, readOnlyDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(readWriteDataSource);

        return routingDataSource;
    }

}