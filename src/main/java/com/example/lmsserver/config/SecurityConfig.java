package com.example.lmsserver.config;

import com.example.lmsserver.klase.enumi.LoginUloga;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable) // nema cookie sesije, pa ni CSRF rizika
            .cors(cors -> {})
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // rest je stateless i tako to
            .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                            /*.requestMatchers(HttpMethod.POST,   "/api/termin/**").authenticated()
                            .requestMatchers(HttpMethod.GET,    "/api/termin/**").authenticated()
                            .requestMatchers(HttpMethod.PUT,    "/api/termin/**").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/api/termin/**").authenticated()

                            .requestMatchers(HttpMethod.POST,   "/api/zadatak/**").authenticated()
                            .requestMatchers(HttpMethod.GET,    "/api/zadatak/**").authenticated()
                            .requestMatchers(HttpMethod.PUT,    "/api/zadatak/**").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/api/zadatak/**").authenticated()
                            .anyRequest().permitAll()*/
                            .anyRequest().authenticated()
                    )
            .oauth2ResourceServer(o -> o.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    // "prevodi" ulogu iz onog enuma u role sto se koristi za @PreAuthorize
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authorities = new JwtGrantedAuthoritiesConverter();
        authorities.setAuthoritiesClaimName("uloga");
        authorities.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authorities);
        return converter;
    }

    //region beanovi za AuthService
    @Bean
    public SecretKey jwtKey(SecurityProperties props) {
        byte[] secret = props.jwtSecret().getBytes(StandardCharsets.UTF_8); // uzima secret iz application.properties
        if (secret.length < 32) {
            throw new IllegalStateException("app.security.jwt-secret mora imati najmanje 32 bajta");
        }
        return new SecretKeySpec(secret, "HmacSHA256");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // vidi prezentaciju za strukturu jwt-a
    @Bean
    public JwtEncoder jwtEncoder(SecretKey jwtKey) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey));
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey jwtKey) {
        return NimbusJwtDecoder.withSecretKey(jwtKey).macAlgorithm(MacAlgorithm.HS256).build();
    }
    //endregion
}
