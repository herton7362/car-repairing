package com.framework.module.auth;

import com.framework.module.member.domain.Member;
import com.framework.module.member.service.MemberService;
import com.kratos.common.AbstractLoginService;
import com.kratos.common.utils.CacheUtils;
import com.kratos.entity.BaseUser;
import com.kratos.exceptions.BusinessException;
import com.kratos.kits.Kits;
import com.kratos.kits.notification.Notification;
import com.kratos.module.auth.UserThread;
import com.kratos.module.auth.domain.OauthClientDetails;
import com.kratos.module.auth.service.OauthClientDetailsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class LoginServiceImpl extends AbstractLoginService {
    private final Kits kits;
    private final TokenEndpoint tokenEndpoint;
    private final MemberService memberService;
    private final OauthClientDetailsService oauthClientDetailsService;

    @Override
    protected Notification getNotification() {
        return kits.notification();
    }

    @Override
    protected TokenEndpoint getTokenEndpoint() {
        return tokenEndpoint;
    }

    @Override
    public ResponseEntity<OAuth2AccessToken> login(String appId, String appSecret, String username, String password) throws Exception {
        if(StringUtils.isBlank(username)) {
            throw new BusinessException("请输入用户名");
        }
        if(StringUtils.isBlank(password)) {
            throw new BusinessException("请输入密码");
        }
        String[] usernameAndClientId = username.split("@");
        if(usernameAndClientId.length == 1) {
            throw new BusinessException("请使用 xxx@xxx 的方式作为用户名登录");
        }
        username = usernameAndClientId[0];
        appId = usernameAndClientId[1];
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findOneByClientId(appId);
        if(oauthClientDetails == null) {
            throw new BusinessException("商户【" + appId + "】不存在");
        }
        return super.login(appId, oauthClientDetails.getClientSecret(), username, password);
    }

    @Override
    public void editPwd(String mobile, String code, String password) throws Exception {
        if(!verifyVerifyCode(mobile, code)) {
            throw new BusinessException(String.format("验证码%s不正确", code));
        }
        Member member = memberService.findOneByLoginName(mobile);
        if(member == null) {
            throw new BusinessException("当前号码未注册");
        }
        member.setPassword(new BCryptPasswordEncoder().encode(password));
        memberService.save(member);
    }

    @Override
    public void register(String mobile, String code, String password) throws Exception {
        if(!verifyVerifyCode(mobile, code)) {
            throw new BusinessException(String.format("验证码%s不正确", code));
        }
        if(findUserByMobile(mobile) != null) {
            throw new BusinessException("该手机号已被注册，请选择找回密码或者直接登录");
        }
        if(StringUtils.isBlank(password)) {
            throw new BusinessException("密码不能为空");
        }
        if(StringUtils.isBlank(mobile)) {
            throw new BusinessException("电话不能为空");
        }
        if(password.length() < 6) {
            throw new BusinessException("密码最短为6位");
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setLoginName(mobile);
        member.setPassword(new BCryptPasswordEncoder().encode(password));
        memberService.save(member);
        clearVerifyCode(mobile);
    }

    @Override
    public BaseUser findUserByMobile(String mobile) throws Exception {
        return memberService.findOneByLoginName(mobile);
    }

    @Autowired
    public LoginServiceImpl(
            Kits kits,
            TokenEndpoint tokenEndpoint,
            MemberService memberService,
            OauthClientDetailsService oauthClientDetailsService
    ) {
        this.kits = kits;
        this.tokenEndpoint = tokenEndpoint;
        this.memberService = memberService;
        this.oauthClientDetailsService = oauthClientDetailsService;
    }
}
