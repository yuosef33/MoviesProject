package com.project.revision.Config.JWT;

import com.project.revision.Service.ClientService;
import com.project.revision.model.Auth;
import com.project.revision.model.Client;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenHandler tokenHandler;
    private final ClientService  clientService;

    public TokenFilter(TokenHandler tokenHandler, ClientService clientService) {
        this.tokenHandler = tokenHandler;
        this.clientService = clientService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getServletPath().contains("login") ||
                request.getServletPath().contains("signup")||
                request.getServletPath().contains("verify") ||
                request.getServletPath().contains("v3") ||
                request.getServletPath().contains("swagger-ui")||
                request.getServletPath().contains("OAuth2")||
                request.getServletPath().contains("forget_password")||
                request.getServletPath().contains("ForgetPassword")

        ){
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=request.getHeader("Authorization");
        if ( token ==null||!token.startsWith("Bearer")){
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        token=token.substring(7);


          if(!tokenHandler.isValidToken(token))
          {
              response.reset();
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              return;
          }


            Client client=clientService.getClientFromToken(token);
            if(Objects.isNull(client)){
                response.reset();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(client,null,convertTheAuth(client.getAuths()));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request,response);

    }

    private List<GrantedAuthority> convertTheAuth (List<Auth> auths){
        List<GrantedAuthority> mainAuths=new ArrayList<>();
        for(Auth auth: auths){
            SimpleGrantedAuthority x =new SimpleGrantedAuthority("ROLE_"+auth.getUser_Role());
            mainAuths.add(x);
        }
        return mainAuths;
    }


}
