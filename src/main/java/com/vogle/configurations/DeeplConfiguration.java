package com.vogle.configurations;

import com.deepl.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeeplConfiguration {

    @Bean
    public DeepLClient deepLClient(@Value("${deepl.api.key}") String deeplApiKey) {
        return new DeepLClient(deeplApiKey);
    }

}
