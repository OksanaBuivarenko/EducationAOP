package t.education.aop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "Education AOP-example api"))
public class SpringAsyncConfig {
}
