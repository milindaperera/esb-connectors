package org.wso2.carbon.connector.tumblr;

import org.apache.synapse.MessageContext;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

public class TumblrGetSubmissions extends AbstractConnector {

	@Override
	public void connect(MessageContext msgCtxt) throws ConnectException {
		//retrieve oauth 1.0a credentials from the message context
		String consumerKey = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_KEY);
		String consumerSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_SECRET);
		String accessToken = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_TOKEN);
		String tokenSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_SECRET);
		
		String destUrl = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_URL_SUBMISSIONS);
		
		//Retrieving parameter values from the message context
		String offsetParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_OFFSET);
		
		//filter parameter doesn't have default value
		String filterParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_FILTER);
		
		//new OAuth request message
		OAuthRequest requestMsg = new OAuthRequest(Verb.GET, destUrl);
		
		//update content type
		//requestMsg.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		//setting query parameters in the http message body
		if (offsetParam != null && offsetParam.isEmpty() == false){
			requestMsg.addQuerystringParameter("offset", offsetParam);
		}
		
		if (filterParam != null && filterParam.isEmpty() == false){
			requestMsg.addQuerystringParameter("filter", filterParam);
		}
		
		//sign the http request message for OAuth 1.0a
		requestMsg = TumblrUtils.signOAuthRequestGeneric(requestMsg, consumerKey, consumerSecret, 
																			accessToken, tokenSecret);
		
		Response response = requestMsg.send();
		
		if (log.isDebugEnabled()){
			log.info("REQUEST TO TUMBLR : Header - " +requestMsg.getHeaders());
			log.info("REQUEST TO TUMBLR : Body - " +requestMsg.getBodyContents());
			log.info("SENDING REQUEST TO TUMBLR : " +destUrl);
			log.info("RECEIVED RESPONSE FROM TUMBLR : Header - " +response.getHeaders());
			log.info("RECEIVED RESPONSE FROM TUMBLR : Body - " +response.getBody());
		}
		//update message payload in message context
		msgCtxt.setProperty("tumblr.response", response.getBody());

	}

}
