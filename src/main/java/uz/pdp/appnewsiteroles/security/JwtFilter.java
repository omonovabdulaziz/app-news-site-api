package uz.pdp.appnewsiteroles.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.appnewsiteroles.service.AuthService;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProwider jwtProwider;
    @Autowired
    AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      //requestdan tokkenni olish
        String token = request.getHeader("Authorization");
        //token borligini va boshlanishi bearer bolishi tekshiryapmiz
        if (token != null && token.startsWith("Bearer")) {
            //ayanan tokenni o'zini qirib oldik
            token = token.substring(7);
            //tekenni validatsiyadan o'tkazdik  (token buzulmaganligini muddati otmaganini tekshirdik)
            boolean validateToken = jwtProwider.validateToken(token);
            if (validateToken) {
                ///token ichidan username oldik
                String userNameFromToken = jwtProwider.getUserNameFromToken(token);
                //username orqali userdetails oldik
                UserDetails userDetails = authService.loadUserByUsername(userNameFromToken);
                //userdetails orqali authentication yaratib oldill

                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

            }
        }
        filterChain.doFilter(request , response);
    }


}