package com.sarangchurch.follower.member.command.ui.argumentresolver;

import com.sarangchurch.follower.auth.domain.exception.UnauthorizedException;
import com.sarangchurch.follower.auth.domain.model.LoginMember;
import com.sarangchurch.follower.member.command.domain.model.Member;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new UnauthorizedException();
        }

        LoginMember loginMember = (LoginMember) authentication.getPrincipal();
        loginMember.validateAppRole();

        return Member.builder()
                .id(loginMember.getId())
                .username(loginMember.getUsername())
                .password(loginMember.getPassword())
                .name(loginMember.getName())
                .birthDate(loginMember.getBirthDate())
                .earlyBorn(loginMember.isEarlyBorn())
                .gender(loginMember.getGender())
                .role(loginMember.getRole())
                .departmentId(loginMember.getDepartmentId())
                .build();
    }
}
