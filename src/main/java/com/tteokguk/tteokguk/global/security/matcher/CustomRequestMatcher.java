package com.tteokguk.tteokguk.global.security.matcher;

import org.springframework.http.HttpMethod;
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
        return new AntPathRequestMatcher("/actuator/health", HttpMethod.GET.toString());
    }

    public RequestMatcher serverInfoEndpoints() {
        return new AntPathRequestMatcher("/actuator/info", HttpMethod.GET.toString());
    }

    public RequestMatcher userEndpoints() {
        return new OrRequestMatcher(
            new AntPathRequestMatcher("/api/v1/user", HttpMethod.GET.toString()),
            new AntPathRequestMatcher("/api/v1/user/all", HttpMethod.GET.toString()),
            new AntPathRequestMatcher("/api/v1/user/{id:\\d+}", HttpMethod.GET.toString()),
            new AntPathRequestMatcher("/api/v1/user/random", HttpMethod.GET.toString())
        );
    }

    public RequestMatcher tempUserEndpoints() {
        return new AntPathRequestMatcher("/api/v1/user/initialization", HttpMethod.POST.toString());
    }

    public RequestMatcher errorEndpoints() {
        return new AntPathRequestMatcher("/error", HttpMethod.GET.toString());
    }
}
