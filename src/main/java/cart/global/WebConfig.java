package cart.global;

import cart.global.authentication.AuthenticationArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    public WebConfig(AuthenticationArgumentResolver authenticationArgumentResolver) {
        this.authenticationArgumentResolver = authenticationArgumentResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/cart").setViewName("cart");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }
}
