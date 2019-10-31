package com.autotrader.web.client.robinhood.authorization;

import com.autotrader.web.client.BrokerCall;
import com.autotrader.web.client.ClientRequest;
import com.autotrader.web.client.ClientResponse;
import com.autotrader.web.client.robinhood.RobinhoodCalls;
import com.autotrader.web.client.robinhood.authorization.request.LoginData;
import com.autotrader.web.client.robinhood.authorization.request.Request;
import com.autotrader.web.client.robinhood.authorization.response.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RetrieveToken implements RobinhoodCalls {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveToken.class);

    private static final String URL = "https://api.robinhood.com/oauth2/token/";

    private RestTemplate restTemplate;

    @Autowired
    public RetrieveToken(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BrokerCall getBrokerCall() {
        return BrokerCall.ROBINHOOD_LOGIN;
    }

    @Override
    public ClientResponse invoke(ClientRequest loginData) {
        LOGGER.info("Beginning RetrieveToken invocation.");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Request> httpEntity = new HttpEntity<>(generateRequest((LoginData)loginData), httpHeaders);

        ResponseEntity<Token> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, Token.class);
        Token token = responseEntity.getBody();

        LOGGER.debug("\n\nToken: " + token.getAccess_token().substring(0, 10));
        LOGGER.info("End of RetrieveToken invocation.");

        return token;
    }

    private Request generateRequest(LoginData loginData) {
        Request request = new Request();
        request.setGrant_type(Constants.ROBINHOOD_GRANT_TYPE);
        request.setScope(Constants.ROBINHOOD_SCOPE);
        request.setClient_id(Constants.ROBINHOOD_CLIENT_ID);

        request.setDevice_token(loginData.getDevice_token());
        request.setUsername(loginData.getUsername());
        request.setPassword(loginData.getPassword());

        return request;
    }
}
