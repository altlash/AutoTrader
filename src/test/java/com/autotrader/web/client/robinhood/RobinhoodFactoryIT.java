package com.autotrader.web.client.robinhood;

import com.autotrader.web.client.BrokerCall;
import com.autotrader.web.client.ClientResponse;
import com.autotrader.web.client.robinhood.authorization.request.LoginData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RobinhoodFactoryIT {

    @Autowired
    private RobinhoodFactory robinhoodFactory;

    @Value("${robinhood.device.token}")
    private String robinhoodDeviceToken;

    @Value("${robinhood.username}")
    private String robinhoodUsername;

    @Value("${robinhood.password}")
    private String robinhoodPassword;

    @Test
    public void invokeTest() {
        ClientResponse response = robinhoodFactory.invoke(BrokerCall.ROBINHOOD_LOGIN, generateLoginData());
        System.out.println("Finished test.");
    }


    private LoginData generateLoginData() {
        LoginData loginData = new LoginData();
        loginData.setDevice_token(robinhoodDeviceToken);
        loginData.setUsername(robinhoodUsername);
        loginData.setPassword(robinhoodPassword);

        return loginData;
    }
}
