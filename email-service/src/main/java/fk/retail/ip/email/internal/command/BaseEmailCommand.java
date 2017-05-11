package fk.retail.ip.email.internal.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import fk.retail.ip.email.configuration.ConnektConfiguration;
import fk.sp.common.extensions.hystrix.JerseyClientBase;

import javax.ws.rs.client.Client;

/**
 * Created by agarwal.vaibhav on 08/05/17.
 */
abstract class BaseEmailCommand extends HystrixCommand implements JerseyClientBase {

    protected final Client client;
    protected final ConnektConfiguration connektConfiguration;
    private static final int DEFAULT_TIME_OUT = 900000;

    /*TODO: look for this and set proper values*/
    BaseEmailCommand(Client client, ConnektConfiguration connektConfiguration) {
        super(
                Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(""))
                .andCommandPropertiesDefaults(HystrixCommandProperties
                .Setter()
                .withExecutionTimeoutInMilliseconds(DEFAULT_TIME_OUT))
        );
        this.client = client;
        this.connektConfiguration = connektConfiguration;
    }

}
