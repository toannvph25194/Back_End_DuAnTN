package com.example.be_duantn.config;
import com.example.be_duantn.jwt.nhan_vien_jwt.NhanVienJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;

    private final NhanVienJwtAuthenticationFilter nhanVienJwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/khachhang/**").permitAll()
                        .requestMatchers("/api/auth/nhanvien/**").permitAll()
                        .requestMatchers("/api/ol/san-pham/**").permitAll()
                        .requestMatchers("/api/ol/san-pham-shop/**").permitAll()
                        .requestMatchers("/api/ol/san-pham-chi-tiet/**").permitAll()
                        .requestMatchers("/api/ol/gio-hang-chi-tiet/**").permitAll()
                        .requestMatchers("/api/ol/gio-hang/**").permitAll()
                        .requestMatchers("/api/ol/check-out/dia-chi-nguoi-dung/**").permitAll()
                        .requestMatchers("/api/ol/check-out/thong-tin-nguoi-dung/**").permitAll()
                        .requestMatchers("/api/ol/voucher/**").permitAll()
                        .requestMatchers("/api/admin/thong-ke/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/admin-chatlieu/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .requestMatchers("/api/admin-danhmuc/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .requestMatchers("/api/admin-sanpham/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .requestMatchers("/api/admin-thuonghieu/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .requestMatchers("/api/admin-xuatxu/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .requestMatchers("/api/admin-mausac/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .requestMatchers("/api/admin-sanphamchitiet/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .requestMatchers("/api/admin-size/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .requestMatchers("/api/admin-image/**").hasAnyAuthority("ADMIN", "NHANVIEN")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(
                        sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(nhanVienJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
