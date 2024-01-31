package com.tteokguk.tteokguk.global.security.matcher;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class CustomRequestMatcher {

    public RequestMatcher authEndpoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/v1/auth/**"),
                new AntPathRequestMatcher("/api/v1/oauth/**")
        );
    }

    public RequestMatcher mainPageEndPoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/v1/tteokguk/new/**"),
                new AntPathRequestMatcher("/api/v1/tteokguk/completion/**")
        );
    }

    public RequestMatcher healthEndpoints() {
        return new AntPathRequestMatcher("/actuator/health");
    }

    public RequestMatcher serverInfoEndpoints() {
        return new AntPathRequestMatcher("/actuator/info");
    }

    public RequestMatcher tempUserEndpoints() {
        return new AntPathRequestMatcher("/api/v1/user/initialization");
    }

    public RequestMatcher errorEndpoints() {
        return new AntPathRequestMatcher("/error");
    }
}
