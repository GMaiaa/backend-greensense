    package com.greensense.security

    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.http.HttpMethod
    import org.springframework.security.authentication.AuthenticationManager
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
    import org.springframework.security.config.annotation.web.builders.HttpSecurity
    import org.springframework.security.config.http.SessionCreationPolicy
    import org.springframework.security.core.userdetails.UserDetailsService
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
    import org.springframework.security.crypto.password.PasswordEncoder
    import org.springframework.security.web.SecurityFilterChain
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

    @Configuration
    class SecurityConfig(
        private val jwtFilter: JWTAuthenticationFilter,
        private val userDetailsService: UserDetailsService
    ) {

        @Bean
fun filterChain(http: HttpSecurity): SecurityFilterChain {
    return http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/register").permitAll() // <-- ADICIONA ESSA LINHA AQUI
                .requestMatchers(HttpMethod.POST, "/api/lixeiras/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/coletas/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        .userDetailsService(userDetailsService)
        .build()
}

        

        @Bean
        fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
            config.authenticationManager

        @Bean
        fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
    }
