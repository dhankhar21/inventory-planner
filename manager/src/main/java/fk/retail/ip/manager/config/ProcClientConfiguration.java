package fk.retail.ip.manager.config;

import lombok.Getter;

/**
 * Created by agarwal.vaibhav on 26/04/17.
 */
@Getter
public class ProcClientConfiguration {
    String url;
    String viewPath;
    String callbackUrl;
    String requirementQueueName;
}
