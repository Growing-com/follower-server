package com.sarangchurch.follower.admin.ui.argumentresolver;

import com.sarangchurch.follower.admin.domain.model.Admin;
import com.sarangchurch.follower.auth.domain.model.LoginMember;
import com.sarangchurch.follower.auth.domain.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthAdminArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthAdmin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new UnauthorizedException();
        }

        LoginMember loginMember = (LoginMember) authentication.getPrincipal();
        loginMember.validateWebRole();

        return new Admin(loginMember.getId(), loginMember.getDepartmentId());
    }
}
