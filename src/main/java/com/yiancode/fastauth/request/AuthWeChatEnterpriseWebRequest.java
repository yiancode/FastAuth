package com.yiancode.fastauth.request;

import com.yiancode.fastauth.cache.AuthStateCache;
import com.yiancode.fastauth.config.AuthConfig;
import com.yiancode.fastauth.config.AuthDefaultSource;
import com.yiancode.fastauth.enums.scope.AuthWeChatEnterpriseWebScope;
import com.yiancode.fastauth.utils.AuthScopeUtils;
import com.yiancode.fastauth.utils.GlobalAuthUtils;
import com.yiancode.fastauth.utils.UrlBuilder;

/**
 * <p>
 * 企业微信网页登录
 * </p>
 *
 * @author liguanhua (347826496(a)qq.com)
 * @since 1.15.9
 */
public class AuthWeChatEnterpriseWebRequest extends AbstractAuthWeChatEnterpriseRequest {
    public AuthWeChatEnterpriseWebRequest(AuthConfig config) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE_WEB);
    }

    public AuthWeChatEnterpriseWebRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE_WEB, authStateCache);
    }

    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("appid", config.getClientId())
            .queryParam("agentid", config.getAgentId())
            .queryParam("redirect_uri", GlobalAuthUtils.urlEncode(config.getRedirectUri()))
            .queryParam("response_type", "code")
            .queryParam("scope", this.getScopes(",", false, AuthScopeUtils.getDefaultScopes(AuthWeChatEnterpriseWebScope.values())))
            .queryParam("state", getRealState(state).concat("#wechat_redirect"))
            .build();
    }
}
