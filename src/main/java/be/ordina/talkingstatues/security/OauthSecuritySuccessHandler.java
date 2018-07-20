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
public class OauthSecuritySuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ApplicationConfigurationUtils util;

    public OauthSecuritySuccessHandler(ApplicationConfigurationUtils util) {
        super();
        this.setUseReferer(true);
        this.util = util;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        System.out.println("Entering Authentication Success Handler from: [" + request.getRequestURI() + "]");
        System.out.println("Origin (Header): [" + request.getHeader("origin") + "]");
        System.out.println("Referer (Header): [" + request.getHeader("referer") + "]");
        System.out.println("Cookie (Header): [" + request.getHeader("cookie") + "]");
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken securityToken = (OAuth2AuthenticationToken) authentication;
            System.out.println("Authorization Server: [" + securityToken.getAuthorizedClientRegistrationId() + "]");
            response.sendRedirect(util.getProperty(ApplicationConfigurationUtils.MOBILE_APP_URL_KEY));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
