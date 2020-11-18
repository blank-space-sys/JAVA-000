package class10;

import class09.User;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(User.class)
@ConditionalOnProperty(prefix = "homework", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnResource(resources = "META-INF/spring.factories")
@EnableConfigurationProperties(User.class)
public class MyAutoConfiguration {

    @Bean
    public User user(){
        return new User();
    }
}
