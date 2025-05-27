package com.sprint.mission.discodeit.common.security.filter;

import com.sprint.mission.discodeit.common.security.SecurityMatchers;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class CustomLogoutFilter extends LogoutFilter {

//    private static final String LOGOUT_URI = "/api/auth/logout";

//    private final PersistentTokenRepository tokenRepository;

    public CustomLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
        LogoutHandler... handlers) {
        super(logoutSuccessHandler, handlers);
    }

    public static CustomLogoutFilter createDefault() {
        CustomLogoutFilter filter = new CustomLogoutFilter(
            new HttpStatusReturningLogoutSuccessHandler(),
            new SecurityContextLogoutHandler()
        );
        filter.setLogoutRequestMatcher(SecurityMatchers.LOGOUT);

        return filter;
    }
}

