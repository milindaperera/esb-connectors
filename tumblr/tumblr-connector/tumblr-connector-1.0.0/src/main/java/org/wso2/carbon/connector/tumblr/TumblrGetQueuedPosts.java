package org.wso2.carbon.connector.tumblr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

public class TumblrGetQueuedPosts extends AbstractConnector{

	private static Log log = LogFactory.getLog(TumblrGetQueuedPosts.class);
	
	@Override
	public void connect(MessageContext msgCtxt) throws ConnectException {
				
		//retrieve oauth 1.0a credentials from the message context
		String consumerKey = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_KEY);
		String consumerSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_SECRET);
		String accessToken = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_TOKEN);
		String tokenSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_SECRET);
		
		String destUrl = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_URL_QUEUEDPOSTS);
		
		//Retrieving parameter values from the message context
		
		String limitParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_LIMIT);
		String filterParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_FILTER);
		String offsetParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_OFFSET);
		
		
		//new OAuth request message
		OAuthRequest requestMsg = new OAuthRequest(Verb.GET, destUrl);

		
		//setting query parameters in the http message body
		if (limitParam != null){
			requestMsg.addQuerystringParameter("limit", limitParam);
		}
		if (offsetParam != null){
			requestMsg.addQuerystringParameter("offset", offsetParam);
		}		
		if (filterParam != null){
			requestMsg.addQuerystringParameter("filter", filterParam);
		}
		
		
		//sign the http request message for OAuth 1.0a
		requestMsg = TumblrUtils.signOAuthRequestGeneric(requestMsg, consumerKey, consumerSecret, 
																			accessToken, tokenSecret);
		
		// TODO change log to debug log level
		log.debug("REQUEST TO TUMBLR : Header - " +requestMsg.getHeaders());
		log.debug("REQUEST TO TUMBLR : Body - " +requestMsg.getBodyContents());
		
		
		// TODO change log to debug log level
		log.debug("SENDING REQUEST TO TUMBLR : " +destUrl);
		
		Response response = requestMsg.send();
		
		// TODO change log to debug log level
		log.debug("RECEIVED RESPONSE FROM TUMBLR : Header - " +response.getHeaders());
		log.debug("RECEIVED RESPONSE FROM TUMBLR : Body - " +response.getBody());

		//update message payload in message context
		msgCtxt.setProperty("tumblr.response", response.getBody());
			
		
	}

}
