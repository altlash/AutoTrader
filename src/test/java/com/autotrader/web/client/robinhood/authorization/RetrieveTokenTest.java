package com.autotrader.web.client.robinhood.authorization;

import com.autotrader.web.client.robinhood.authorization.request.LoginData;
import com.autotrader.web.client.robinhood.authorization.request.Request;
import com.autotrader.web.client.robinhood.authorization.response.Token;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RetrieveTokenTest {
    private static final String URL = "https://api.robinhood.com/oauth2/token/";
    private static final String TOKEN = "Some token string";
    private static final String USERNAME = "Some username";
    private static final String PASSWORD = "Some password";
    private static final String DEVICE_TOKEN = "Some device token";

    @Mock
    private RestTemplate mockRestTemplate;

    private RetrieveToken retrieveToken;

    @Before
    public void setup() {
        retrieveToken = new RetrieveToken(mockRestTemplate);
    }

    @Test
    public void invokeTest() {
        when(mockRestTemplate.exchange(anyString(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<?>>any(), Mockito.<Class<?>>any())).thenReturn(generateResponseEntity());



        Token response = (Token)retrieveToken.invoke(generateLoginData());



        assertEquals(TOKEN, response.getAccess_token());

        ArgumentCaptor<HttpEntity<Request>> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(mockRestTemplate).exchange(eq(URL), eq(HttpMethod.POST), httpEntityArgumentCaptor.capture(), eq(Token.class));

        HttpEntity<Request> capturedHttpEntity = httpEntityArgumentCaptor.getValue();

        HttpHeaders capturedHeaders = capturedHttpEntity.getHeaders();
        assertEquals(MediaType.APPLICATION_JSON, capturedHeaders.getContentType());

        Request capturedRequest = capturedHttpEntity.getBody();
        assertEquals(Constants.ROBINHOOD_GRANT_TYPE, capturedRequest.getGrant_type());
        assertEquals(Constants.ROBINHOOD_CLIENT_ID, capturedRequest.getClient_id());
        assertEquals(Constants.ROBINHOOD_SCOPE, capturedRequest.getScope());
        assertEquals(USERNAME, capturedRequest.getUsername());
        assertEquals(PASSWORD, capturedRequest.getPassword());
        assertEquals(DEVICE_TOKEN, capturedRequest.getDevice_token());
    }

    private ResponseEntity generateResponseEntity() {
        Token token = new Token();
        token.setAccess_token(TOKEN);

        return new ResponseEntity(token, HttpStatus.OK);
    }

    private LoginData generateLoginData() {
        LoginData loginData = new LoginData();
        loginData.setUsername(USERNAME);
        loginData.setPassword(PASSWORD);
        loginData.setDevice_token(DEVICE_TOKEN);

        return loginData;
    }


}
