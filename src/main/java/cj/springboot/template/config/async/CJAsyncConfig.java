package cj.springboot.template.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;

@Component
public class CJAsyncConfig {
    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    @Bean
    public CJDeferredResultProcessInterceptor cjDeferredResultProcessInterceptor() {
        return new CJDeferredResultProcessInterceptor();
    }
}
