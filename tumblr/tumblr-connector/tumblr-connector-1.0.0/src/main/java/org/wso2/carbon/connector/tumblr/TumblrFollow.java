package org.wso2.carbon.connector.tumblr;

import org.apache.synapse.MessageContext;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

public class TumblrFollow extends AbstractConnector {

	@Override
	public void connect(MessageContext msgCtxt) throws ConnectException {
		
		String consumerKey = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_KEY);
		String consumerSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_SECRET);
		String accessToken = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_TOKEN);
		String tokenSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_SECRET);
		
		String destUrl = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_URL_FOLLOW);
		
		String urlParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_URL);
		
		//new OAuth request message
		OAuthRequest requestMsg = new OAuthRequest(Verb.POST, destUrl);
		
		//update content type
		requestMsg.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		//setting query parameters in the http message body
		requestMsg.addBodyParameter("url", urlParam);
		
		//sign the http request message for OAuth 1.0a
		requestMsg = TumblrUtils.signOAuthRequestGeneric(requestMsg, consumerKey, consumerSecret, 
																			accessToken, tokenSecret);
		
		log.debug("REQUEST TO TUMBLR : Header - " +requestMsg.getHeaders());
		log.debug("REQUEST TO TUMBLR : Body - " +requestMsg.getBodyContents());
		log.debug("SENDING REQUEST TO TUMBLR : " +destUrl);
		
		Response response = requestMsg.send();
		
		log.debug("RECEIVED RESPONSE FROM TUMBLR : Header - " +response.getHeaders());
		log.debug("RECEIVED RESPONSE FROM TUMBLR : Body - " +response.getBody());

		//update message payload in message context
		msgCtxt.setProperty("tumblr.response", response.getBody());
		
	}

}
