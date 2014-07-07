package org.wso2.carbon.connector.tumblr;

import org.apache.axiom.om.OMException;
import org.apache.axis2.AxisFault;
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
		String typeParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TYPE);
		/*if (typeParam == null || typeParam != "chat"){
			typeParam = "chat"; //default value = chat
		}*/
		
		String stateParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_STATE);
		/*if (stateParam == null){
			stateParam = "published"; //default value = published
		}*/
		
		String tagsParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TAGS);//no default value
		String tweetParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TWEET);//no default value
		
		String formatParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_FORMAT);
		/*if (formatParam == null){
			formatParam = "html"; //default value = html
		}*/
		
		String slugParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_SLUG);//no default value
			
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
			
			log.info("CREATING TEXT POST");
			
			String titleParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TITLE);//no default value
			if (titleParam != null){
				requestMsg.addBodyParameter("title", titleParam);
			}
			
			String bodyParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_BODY);//no default value
			if (bodyParam != null){
				requestMsg.addBodyParameter("body", bodyParam);
			}
			
		}else if (typeParam.equals("quote")){
			
			log.info("CREATING QUOTE POST");
			
			String quoteParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_QUOTE);//no default value
			if (quoteParam != null){
				requestMsg.addBodyParameter("quote", quoteParam);
			}
			String soureParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_SOURCE);//no default value
			if (soureParam != null){
				requestMsg.addBodyParameter("source", soureParam);
			}
			
		}else if (typeParam.equals("link")){
			
			log.info("CREATING LINK POST");
			
			String titleParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TITLE);//no default value
			if (titleParam != null){
				requestMsg.addBodyParameter("title", titleParam);
			}
			
			String urlParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_SOURCE);//no default value
			if (urlParam != null){
				requestMsg.addBodyParameter("url", urlParam);
			}
			
			String descriptionParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_DESCRIPTION);//no default value
			if (descriptionParam != null){
				requestMsg.addBodyParameter("description", descriptionParam);
			}
			
		}else if (typeParam.equals("chat")){
			
			log.info("CREATING CHAT POST");
			
			String titleParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_TITLE);//no default value
			if (titleParam != null){
				requestMsg.addBodyParameter("title", titleParam);
			}
			
			String conversationParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_CONVERSATION);//no default value
			if (conversationParam != null){
				requestMsg.addBodyParameter("conversation", conversationParam);
			}
			
		}else if (typeParam.equals("audio")){
			
			log.info("CREATING AUDIO POST");
			
			String captionParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_CAPTION);//no default value
			if (captionParam != null){
				requestMsg.addBodyParameter("caption", captionParam);
			}
			
			String external_urlParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_SOURCE);//no default value
			if (external_urlParam != null){
				requestMsg.addBodyParameter("external_url", external_urlParam);
			}
			
		}else if (typeParam.equals("video")){
			
			log.info("CREATING VIDEO POST");
			
			String captionParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_CAPTION);//no default value
			if (captionParam != null){
				requestMsg.addBodyParameter("caption", captionParam);
			}
			
			String embedParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_EMBED);//no default value
			if (embedParam != null){
				requestMsg.addBodyParameter("embed", embedParam);
			}
			
			
		}else{
			//UNEXPECTED ERROR : DOES NOT REACH ELSE BLOCK
			// TODO : PRINT ERROR
		}
		
		
		//sign the http request message for OAuth 1.0a
		requestMsg = TumblrUtils.signOAuthRequestGeneric(requestMsg, consumerKey, consumerSecret, 
																			accessToken, tokenSecret);
		
		// TODO change log to debug log level
		log.info("REQUEST TO TUMBLR : Header - " +requestMsg.getHeaders());
		log.info("REQUEST TO TUMBLR : Body - " +requestMsg.getBodyContents());
		
		
		// TODO change log to debug log level
		log.info("SENDING REQUEST TO TUMBLR : " +destUrl);
		
		Response response = requestMsg.send();
		
		// TODO change log to debug log level
		log.info("RECEIVED RESPONSE FROM TUMBLR : Header - " +response.getHeaders());
		log.info("RECEIVED RESPONSE FROM TUMBLR : Body - " +response.getBody());

		//update message payload in message context
		try {
			TumblrUtils.addPayloadToMsgCntxt(msgCtxt, response.getBody());
		} catch (AxisFault e) {
			// TODO : print error message : Error while converting json response to xml representation
			e.printStackTrace();
		} catch (OMException e) {
			// TODO : print error message : Error in retrieving soap body in the soap envelop in message context
			e.printStackTrace();
		} catch (Exception e) {
			//TODO : print error message
			e.printStackTrace();
		}
		
	}

}
