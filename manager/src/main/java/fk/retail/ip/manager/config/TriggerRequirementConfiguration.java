package fk.retail.ip.manager.config;

import lombok.Data;

/**
 * Created by agarwal.vaibhav on 28/04/17.
 */
@Data
public class TriggerRequirementConfiguration {
    String url;
    String projectionQueueName;
    String fetchDataBatchSize;
    String createReqBatchSize;
}
