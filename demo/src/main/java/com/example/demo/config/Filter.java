package com.example.demo.config;

import com.example.demo.Service.TokenService;
import com.example.demo.entity.Account;
import com.example.demo.exception.AuthorizeException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {

    //giúp quăng lỗi
    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    TokenService tokenService;

    List<String> PUBLIC_API = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/login",
            "/api/register"

    );// ai cũng truy cập được

    boolean isPermitted(String uri) {
        AntPathMatcher matcher = new AntPathMatcher();

        return PUBLIC_API.stream().anyMatch(item -> matcher.match(item, uri));
    }
    // xem coi thử api pulic ko , nếu ko thì check role

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //   filterChain.doFilter(request, response);
        // cho phép truy cập vào controller
        //check trước khi cho truy cập ( phân quyền)

        String uri = request.getRequestURI();
        if(isPermitted(uri)) {
            //public api  cho nó truy cập luôn
            filterChain.doFilter(request, response);
        }else {
            // không pha public API => check role
            //để check role thì kiểm tra nó có tr về token hay ko
            String token = getToken(request);

            if(token == null ){
                // chưa đăng nhập => quăng lỗi
                handlerExceptionResolver.resolveException(request,response,null,new AuthorizeException("Empty token"));
            }
            Account account = null ;

            try{
                account = tokenService.getStudentByToken(token);

            }catch (MalformedJwtException malformedJwtException){
                //token ko chuẩn
                handlerExceptionResolver.resolveException(request,response,null,new AuthorizeException("Audentication is valid"));

            }
            // token hết hạn
            catch (ExpiredJwtException expiredJwtException){
                handlerExceptionResolver.resolveException(request,response,null,new AuthorizeException("Audentication is valid"));

            }
            catch (Exception exception){
                handlerExceptionResolver.resolveException(request,response,null,new AuthorizeException("Audentication is valid"));

            }
            // => token chuẩn
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(account,token, account.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        }

    }

    String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null) return null;

        // ngan chan loi
        token = token.substring(7);
        return token;
    }
    // laays ở vị trí thứ 7
    //token sẽ trả Bearer ajsdalksidk


}
