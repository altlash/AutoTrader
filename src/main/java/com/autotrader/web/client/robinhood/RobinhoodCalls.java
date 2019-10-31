package com.autotrader.web.client.robinhood;

import com.autotrader.web.client.BrokerCall;
import com.autotrader.web.client.ClientRequest;
import com.autotrader.web.client.ClientResponse;

public interface RobinhoodCalls {
    ClientResponse invoke(ClientRequest loginData);
    BrokerCall getBrokerCall();
}
