package com.autotrader.web;

import com.autotrader.web.client.BrokerCall;
import com.autotrader.web.client.robinhood.RobinhoodFactory;
import com.autotrader.web.client.robinhood.authorization.request.LoginData;
import com.autotrader.web.client.robinhood.authorization.response.Token;
import com.autotrader.web.model.TestModel;
import com.autotrader.web.model.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
    public TestModel testServices() {
        LOGGER.info("REST endpoint invoked: /testServices  Running Tests:");

        List<TestResult> results = new ArrayList<>();
        int pass = 0;
        int fail = 0;

        TestResult loginResult = new TestResult();
        loginResult.setTest("LOGIN");
        if (testLogin()) {
            loginResult.setResult("PASSED");
            LOGGER.info("Login: PASSED");
            pass++;
        } else {
            loginResult.setResult("FAILED");
            LOGGER.info("Login: FAILED");
            fail++;
        }
        results.add(loginResult);

        TestResult parameterStoreResult = new TestResult();
        parameterStoreResult.setTest("PARAMETER STORE");
        if (testParameterStore()) {
            parameterStoreResult.setResult("PASSED");
            LOGGER.info("Parameter Store: PASSED");
            pass++;
        } else {
            parameterStoreResult.setResult("FAILED");
            LOGGER.info("Parameter Store: FAILED");
            fail++;
        }
        results.add(parameterStoreResult);

        //Add in calls to different test functionality and add to result string and counters.

        String overallResults;
        if (pass == 0 && fail == 0) {
            overallResults = "WARNING: No tests ran.";
            LOGGER.info("WARNING: No tests ran.");
        } else {
            overallResults = "Completed running tests.  Passed: " + pass + " Failed: " + fail;
            LOGGER.info("Completed running tests.  Passed: " + pass + " Failed: " + fail);
        }

        TestModel testModel = new TestModel();
        testModel.setResults(results);
        testModel.setOverallResults(overallResults);

        return testModel;
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
