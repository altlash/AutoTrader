package com.autotrader.web.client.robinhood.authorization.request;

import com.autotrader.web.client.ClientRequest;
import lombok.Data;

@Data
public class LoginData extends ClientRequest {

    private String device_token;
    private String username;
    private String password;
}
