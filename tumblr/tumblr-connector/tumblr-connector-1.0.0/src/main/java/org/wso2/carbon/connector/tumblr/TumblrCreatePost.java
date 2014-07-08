package org.wso2.carbon.connector.tumblr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

public class TumblrCreatePost extends AbstractConnector {

	private static Log log = LogFactory.getLog(TumblrCreatePost.class);
	
	@Override
	public void connect(MessageContext msgCtxt) throws ConnectException {
		
		//retrieve oauth 1.0a credentials from the message context
		String consumerKey = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_KEY);
		String consumerSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_SECRET);
		String accessToken = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_TOKEN);
		String tokenSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_SECRET);
		
		String destUrl = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_URL_CREATEPOST);
	
		//Retrieving parameter values from the message context
		//----Common post parameters
		String typeParam = "";
		typeParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TYPE);
		String stateParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_STATE);
		String tagsParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TAGS);
		String tweetParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TWEET);
		String formatParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_FORMAT);		
		String slugParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_SLUG);
			
		//new OAuth request message
		OAuthRequest requestMsg = new OAuthRequest(Verb.POST, destUrl);
		
		//update content type
		requestMsg.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		
		//setting query parameters in the http message 
		requestMsg.addBodyParameter("type", typeParam);
		
		if (stateParam != null){
			requestMsg.addBodyParameter("state", stateParam);
		}
		
		
		if (tagsParam != null){
			requestMsg.addBodyParameter("tags", tagsParam);
		}
		
		if (tweetParam != null){
			requestMsg.addBodyParameter("tweet", tweetParam);
		}
		
		if (formatParam != null){
			requestMsg.addBodyParameter("format", formatParam);
		}
		
		
		if (slugParam != null){
			requestMsg.addBodyParameter("slug", slugParam);
		}
		
		//----Specialized post parameters
		if (typeParam.equals("text")){
			
			log.debug("CREATING TEXT POST");
			
			String titleParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TITLE);
			if (titleParam != null){
				requestMsg.addBodyParameter("title", titleParam);
			}
			
			String bodyParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_BODY);
			if (bodyParam != null){
				requestMsg.addBodyParameter("body", bodyParam);
			}
			
		}else if (typeParam.equals("quote")){
			
			log.debug("CREATING QUOTE POST");
			
			String quoteParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_QUOTE);
			if (quoteParam != null){
				requestMsg.addBodyParameter("quote", quoteParam);
			}
			String soureParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_SOURCE);
			if (soureParam != null){
				requestMsg.addBodyParameter("source", soureParam);
			}
			
		}else if (typeParam.equals("link")){
			
			log.debug("CREATING LINK POST");
			
			String titleParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TITLE);
			if (titleParam != null){
				requestMsg.addBodyParameter("title", titleParam);
			}
			
			String urlParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_SOURCE);
			if (urlParam != null){
				requestMsg.addBodyParameter("url", urlParam);
			}
			
			String descriptionParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_DESCRIPTION);
			if (descriptionParam != null){
				requestMsg.addBodyParameter("description", descriptionParam);
			}
			
		}else if (typeParam.equals("chat")){
			
			log.debug("CREATING CHAT POST");
			
			String titleParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TITLE);
			if (titleParam != null){
				requestMsg.addBodyParameter("title", titleParam);
			}
			
			String conversationParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_CONVERSATION);
			if (conversationParam != null){
				requestMsg.addBodyParameter("conversation", conversationParam);
			}
			
		}else if (typeParam.equals("audio")){
			
			log.debug("CREATING AUDIO POST");
			
			String captionParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_CAPTION);
			if (captionParam != null){
				requestMsg.addBodyParameter("caption", captionParam);
			}
			
			String external_urlParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_SOURCE);
			if (external_urlParam != null){
				requestMsg.addBodyParameter("external_url", external_urlParam);
			}
			
		}else if (typeParam.equals("video")){
			
			log.debug("CREATING VIDEO POST");
			
			String captionParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_CAPTION);
			if (captionParam != null){
				requestMsg.addBodyParameter("caption", captionParam);
			}
			
			String embedParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_EMBED);
			if (embedParam != null){
				requestMsg.addBodyParameter("embed", embedParam);
			}
			
			
		}
		
		//sign the http request message for OAuth 1.0a
		requestMsg = TumblrUtils.signOAuthRequestGeneric(requestMsg, consumerKey, consumerSecret, 
																			accessToken, tokenSecret);
		log.debug("REQUEST TO TUMBLR : Header - " +requestMsg.getHeaders());
		log.debug("REQUEST TO TUMBLR : Body - " +requestMsg.getBodyContents());
		log.debug("SENDING REQUEST TO TUMBLR : " +destUrl);
		
		//send request to tumblr
		Response response = requestMsg.send();
		
		log.debug("RECEIVED RESPONSE FROM TUMBLR : Header - " +response.getHeaders());
		log.debug("RECEIVED RESPONSE FROM TUMBLR : Body - " +response.getBody());

		//update message payload in message context
		msgCtxt.setProperty("tumblr.response", response.getBody());
		
	}

}
