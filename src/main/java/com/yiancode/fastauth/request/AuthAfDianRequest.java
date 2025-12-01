package com.yiancode.fastauth.request;

import com.alibaba.fastjson.JSONObject;
import com.yiancode.fastauth.cache.AuthStateCache;
import com.yiancode.fastauth.config.AuthConfig;
import com.yiancode.fastauth.config.AuthDefaultSource;
import com.yiancode.fastauth.enums.AuthUserGender;
import com.yiancode.fastauth.model.AuthCallback;
import com.yiancode.fastauth.model.AuthToken;
import com.yiancode.fastauth.model.AuthUser;
import com.yiancode.fastauth.utils.HttpUtils;
import com.yiancode.fastauth.utils.UrlBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * 爱发电
 *
 * @author handy
 */
public class AuthAfDianRequest extends AuthDefaultRequest {

    public AuthAfDianRequest(AuthConfig config) {
        super(config, AuthDefaultSource.AFDIAN);
    }

    public AuthAfDianRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.AFDIAN, authStateCache);
    }

    @Override
    public AuthToken getAccessToken(AuthCallback authCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", config.getClientId());
        params.put("client_secret", config.getClientSecret());
        params.put("code", authCallback.getCode());
        params.put("redirect_uri", config.getRedirectUri());
        String response = new HttpUtils(config.getHttpConfig()).post(AuthDefaultSource.AFDIAN.accessToken(), params, false).getBody();
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        String userId = accessTokenObject.getJSONObject("data").getString("user_id");
        return AuthToken.builder().userId(userId).build();
    }

    @Override
    public AuthUser getUserInfo(AuthToken authToken) {
        return AuthUser.builder()
            .uuid(authToken.getUserId())
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     */
    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("response_type", "code")
            .queryParam("scope", "basic")
            .queryParam("client_id", config.getClientId())
            .queryParam("redirect_uri", config.getRedirectUri())
            .queryParam("state", getRealState(state))
            .build();
    }

}
