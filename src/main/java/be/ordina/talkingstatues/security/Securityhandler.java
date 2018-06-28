package be.ordina.talkingstatues.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Securityhandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public Securityhandler() {
        super();
        this.setUseReferer(true);
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException  {
        System.out.println("Entering Authentication Success Handler from: [" + request.getRequestURI() + "]");
        System.out.println("Origin (Header): [" + request.getHeader("origin") + "]");
        System.out.println("Referer (Header): [" + request.getHeader("referer") + "]");
        System.out.println("Cookie (Header): [" + request.getHeader("cookie") + "]");

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken securityToken =(OAuth2AuthenticationToken) authentication;
            System.out.println("Authorization Server: ["  + securityToken.getAuthorizedClientRegistrationId() + "]");
        } else {
//           response.sendRedirect(request.getHeader("referer"));
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}