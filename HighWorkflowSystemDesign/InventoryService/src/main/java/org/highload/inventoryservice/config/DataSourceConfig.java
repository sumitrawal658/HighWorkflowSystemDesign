import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().url("jdbc:mysql://write-db:3306/app").build();
    }

    @Bean
    public DataSource readDataSource() {
        return DataSourceBuilder.create().url("jdbc:mysql://read-db:3306/app").build();
    }
}