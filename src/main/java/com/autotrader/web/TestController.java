package com.autotrader.web;

import com.autotrader.web.client.BrokerCall;
import com.autotrader.web.client.robinhood.RobinhoodFactory;
import com.autotrader.web.client.robinhood.authorization.request.LoginData;
import com.autotrader.web.client.robinhood.authorization.response.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RobinhoodFactory factory;

    @Value("${dynamodb.access.key}")
    private String dynamoDBAccessKey;

    @Value("${dynamodb.access.secret}")
    private String dynamoDBAccessSecret;

    @Value("${robinhood.device.token}")
    private String robinhoodDeviceToken;

    @Value("${robinhood.username}")
    private String robinhoodUsername;

    @Value("${robinhood.password}")
    private String robinhoodPassword;

    @RequestMapping("/testServices")
    public String testServices() {
        LOGGER.warn("REST endpoint invoked: /testServices");

        int pass = 0;
        int fail = 0;

        String result = "Successfully invoked endpoint.  Running tests: \n";

        if (testLogin()) {
            result += "Login: PASSED\n";
            pass++;
        } else {
            result += "Login: FAILED\n";
            fail++;
        }

        if (testParameterStore()) {
            result += "Parameter Store: PASSED\n";
            pass++;
        } else {
            result += "Parameter Store: FAILED\n";
            fail++;
        }

        //Add in calls to different test functionality and add to result string and counters.

        if (pass == 0 && fail == 0) {
            result += "WARNING: No tests ran.";
        } else {
            result += "Completed running tests.  Passed: " + pass + " Failed: " + fail;
        }

        LOGGER.warn("Test Services endpoint result: " + result);
        return result;
    }

    private boolean testLogin() {
        Token token = (Token)factory.invoke(BrokerCall.ROBINHOOD_LOGIN, generateLoginData());

        if (token == null) {
            return false;
        } else if("".equals(token.getAccess_token())) {
            return false;
        }

        return true;
    }

    private LoginData generateLoginData() {
        LoginData loginData = new LoginData();
        loginData.setDevice_token(robinhoodDeviceToken);
        loginData.setUsername(robinhoodUsername);
        loginData.setPassword(robinhoodPassword);

        return loginData;
    }

    private boolean testParameterStore() {
        LOGGER.info("Testing Parameter Store");

        if (dynamoDBAccessKey == null || dynamoDBAccessKey.isEmpty()) {
            return false;
        } else if (dynamoDBAccessSecret == null || dynamoDBAccessSecret.isEmpty()) {
            return false;
        }

        return true;
    }


}
