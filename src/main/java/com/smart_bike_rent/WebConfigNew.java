package com.smart_bike_rent;

import io.swagger.models.HttpMethod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.Collections;

@EnableWebMvc
@Configuration
public class WebConfigNew {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
       // config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://bike-rent-frontend-38105283785e.herokuapp.com/");
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));

        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()
        ));
        config.setExposedHeaders(Collections.singletonList("Content-Disposition"));
        config.setMaxAge(3600L); // Set max age for preflight requests
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source); // Directly return CorsFilter instead of FilterRegistrationBean
    }
}