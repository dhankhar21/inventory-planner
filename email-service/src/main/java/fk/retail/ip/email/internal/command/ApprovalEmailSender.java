package fk.retail.ip.email.internal.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import fk.retail.ip.email.client.ConnektClient;
import fk.retail.ip.email.internal.enums.ApprovalEmailParams;
import fk.retail.ip.email.internal.enums.EmailParams;
import fk.retail.ip.email.model.*;
import fk.retail.ip.email.internal.Constants;
import fk.retail.ip.email.internal.repository.EmailDetailsRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 08/05/17.
 */
@Slf4j
public class ApprovalEmailSender extends SendEmail {

    @Inject
    public ApprovalEmailSender(ConnektClient connektClient, EmailDetailsRepository emailDetailsRepository) {
        super(emailDetailsRepository, connektClient);
    }

//    @Override
    public void send(Map<EmailParams, String> params, String stencilId){

        if (params.get(ApprovalEmailParams.GROUPNAME).isEmpty()) {
            log.info("no group chosen");
            return;
        }

        ConnektPayload connektPayload = new ConnektPayload();
        connektPayload.setStencilId(stencilId);

        ApprovalChannelDataModel approvalChannelDataModel = new ApprovalChannelDataModel();
        approvalChannelDataModel.setUserName(params.get(ApprovalEmailParams.USERNAME));
        approvalChannelDataModel.setUser(params.get(ApprovalEmailParams.USER));
        approvalChannelDataModel.setTimestamp(params.get(ApprovalEmailParams.TIMESTAMP));
        approvalChannelDataModel.setGroupName(params.get(ApprovalEmailParams.GROUPNAME));
        approvalChannelDataModel.setLink(params.get(ApprovalEmailParams.LINK));
        connektPayload.setChannelDataModel(approvalChannelDataModel);

        EmailDetails emailDetailsList = getEmailDetails(stencilId, params.get(ApprovalEmailParams.GROUPNAME).toString());
        if (emailDetailsList == null) {
            log.info("no emailing list found for given stencilId and group");
            return;
        }

        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setType(Constants.APPROVAL_CHANNEL_INFO_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            /*Parse the json emailing list fetched from db*/
            List<Person> toList = objectMapper.readValue(emailDetailsList.getToList(),
                    new TypeReference<List<Person>>(){});
            channelInfo.setTo(toList);
            Person from = objectMapper.readValue(emailDetailsList.getFrom(), Person.class);
            channelInfo.setFrom(from);
        } catch(IOException ex) {
            log.info("unable to parse the json value of emailing list");
            return;
        }

        channelInfo.setCc(emailDetailsList.getCcList());

        connektPayload.setChannelInfo(channelInfo);
        connektPayload.setContextId(Constants.APPROVAL_CONTEXT_ID);
        connektClient.sendEmail(connektPayload);

    }
}
