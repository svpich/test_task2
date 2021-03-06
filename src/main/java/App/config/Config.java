package App.config;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "App")
public class Config {

    @Bean
    public URIBuilder urlBuilderBean() {
        return new URIBuilder();
    }
}
