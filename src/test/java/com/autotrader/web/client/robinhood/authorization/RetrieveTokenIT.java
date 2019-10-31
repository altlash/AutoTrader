package com.autotrader.web.client.robinhood.authorization;

import com.autotrader.web.client.robinhood.authorization.request.LoginData;
import com.autotrader.web.client.robinhood.authorization.response.Token;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetrieveTokenIT {

    @Autowired
    private RetrieveToken retrieveToken;

    @Value("${robinhood.device.token}")
    private String robinhoodDeviceToken;

    @Value("${robinhood.username}")
    private String robinhoodUsername;

    @Value("${robinhood.password}")
    private String robinhoodPassword;

    @Test
    public void springTest() {
        Token token = (Token)retrieveToken.invoke(generateLoginData());
        assertNotNull(token);
        assertTrue(StringUtils.isNotBlank(token.getAccess_token()));
    }


    private LoginData generateLoginData() {
        LoginData loginData = new LoginData();
        loginData.setDevice_token(robinhoodDeviceToken);
        loginData.setUsername(robinhoodUsername);
        loginData.setPassword(robinhoodPassword);

        return loginData;
    }



}
