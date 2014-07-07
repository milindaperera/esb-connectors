package org.wso2.carbon.connector.tumblr;

import java.io.ByteArrayInputStream;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axis2.AxisFault;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonBuilder;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TumblrApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import org.apache.synapse.commons.json.JsonUtil;

public class TumblrUtils {
	
	
	 /**
     * Method to sign generic OAuthRequest using provided credentialsfor OAuth 1.0a using Scrib library
     *
     * @param request  			OAuthRequest to sign
     * @param consumerKey   	Consumer key of the application
     * @param consumerSecret	Consumer secret of the consumer key
     * @param accessToken		Access token for protected resource
     * @param tokenSecret		Token secret of the access token
     *
     * @return Signed OAuthRequest
     */
	public static OAuthRequest signOAuthRequestGeneric(OAuthRequest request, String consumerKey, 
												String consumerSecret, String accessToken, 
												String tokenSecret){
		
		OAuthService service = new ServiceBuilder().provider(TumblrApi.class)
														.apiKey(consumerKey)
														.apiSecret(consumerSecret)
														.build();
		
		Token userAccessToken = new Token(accessToken, tokenSecret);
		
		service.signRequest(userAccessToken, request);
		
		return request;
	}
	
	
	
	/**
     * Method to add xml representation of json payload as response to given message context 
     *
     * @param msgContext  		Message context
     * @param payLoad   		Json payload string which need to add to the message body 
     * @param consumerSecret	Consumer secret of the consumer key
     *
     */
	public static void addPayloadToMsgCntxt(MessageContext msgContext, String payLoad) throws AxisFault, OMException, Exception{

		
		OMElement ele = JsonUtil.toXml(new ByteArrayInputStream(payLoad.getBytes()), true);
		SOAPBody soapMsgBody = msgContext.getEnvelope().getBody();
		soapMsgBody.addChild(ele);
		
	}
	
}
