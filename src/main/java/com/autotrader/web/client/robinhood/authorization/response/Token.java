package com.autotrader.web.client.robinhood.authorization.response;

import com.autotrader.web.client.ClientResponse;
import lombok.Data;

@Data
public class Token extends ClientResponse {
    private String access_token;
}
