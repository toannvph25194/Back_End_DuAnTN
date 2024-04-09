
package com.example.be_duantn.jwt.nhan_vien_jwt;

import com.example.be_duantn.service.authentication_service.nhan_vien_service_impl.NhanVienDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class NhanVienJwtAuthenticationFilter extends OncePerRequestFilter {
    private final NhanVienJwtService nhanVienJwtService;

    private final NhanVienDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization :" + authHeader);
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Người dùng chưa đăng nhập");
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = nhanVienJwtService.extractUsername(jwt); // TODO Chích xuất người dùng từ JWT TOKEN
        System.out.println("Tên Người Dùng :" + username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            NhanVienCustomDetails nhanVienCustomDetails = (NhanVienCustomDetails) this.userDetailService
                    .loadUserByUsername(username);
            if (nhanVienJwtService.isTokenValid(jwt, nhanVienCustomDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        nhanVienCustomDetails,
                        null,
                        nhanVienCustomDetails.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("Authentication :" + SecurityContextHolder.getContext().getAuthentication());
            }
        }
        filterChain.doFilter(request, response);
    }



}
