package com.autotrader.web.client.robinhood.authorization.request;

import lombok.Data;

@Data
public class Request {
    private String grant_type;
    private String scope;
    private String client_id;


    private String device_token;
    private String username;
    private String password;
}
