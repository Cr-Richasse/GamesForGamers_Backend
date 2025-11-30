package com.example.GamesForGamers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF (no es necesario para APIs REST modernas con React)
                .csrf(csrf -> csrf.disable())

                // Activamos la configuración de CORS definida más abajo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Definimos permisos de rutas
                .authorizeHttpRequests(auth -> auth
                        // PERMITIR ACCESO PÚBLICO A:
                        // - /api/usuarios/** (Para poder registrarse)
                        // - /api/videojuegos/** (Para ver el catálogo sin loguearse)
                        // - /api/auth/** (Para el futuro endpoint de Login)
                        .requestMatchers("/api/usuarios/**", "/api/videojuegos/**", "/api/auth/**").permitAll()

                        // Cualquier otra ruta requiere estar logueado (lo veremos en la siguiente fase)
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    /**
     * Configuración Global de CORS.
     * Esto permite que tu Frontend (en el puerto 5173) envíe peticiones al Backend (8080)
     * sin que el navegador lo bloquee por seguridad.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir SOLAMENTE tu frontend local (Vite suele usar el 5173)
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Este es el "motor" de encriptación (BCrypt).
     * Spring lo inyectará automáticamente en el UsuarioController.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}