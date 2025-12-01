package com.yiancode.fastauth.request;

import com.yiancode.fastauth.config.AuthConfig;
import com.yiancode.fastauth.utils.AuthStateUtils;
import org.junit.Test;

public class AuthWeChatMpRequestTest {

    @Test
    public void authorize() {

        AuthRequest request = new AuthWeChatMpRequest(AuthConfig.builder()
            .clientId("a")
            .clientSecret("a")
            .redirectUri("https://www.justauth.cn")
            .build());
        System.out.println(request.authorize(AuthStateUtils.createState()));
    }
}
