/*
 * Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.tumblr;

import org.apache.synapse.MessageContext;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

public class TumblrGetFollowers extends AbstractConnector {

	@Override
	public void connect(MessageContext msgCtxt) throws ConnectException {
		
		String consumerKey = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_KEY);
		String consumerSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_CONSUMER_SECRET);
		String accessToken = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_TOKEN);
		String tokenSecret = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_ACCESS_SECRET);
		
		String destUrl = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_URL_FOLLOWERS);
		
		//Retrieving parameter values from the message context
		String limitParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_LIMIT);
		String offsetParam = (String) msgCtxt.getProperty(TumblrConstants.TUMBLR_PARAMETER_OFFSET);
		
		
		//new OAuth request message
		OAuthRequest requestMsg = new OAuthRequest(Verb.GET, destUrl);
		
		//update content type
		//requestMsg.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		//setting query parameters in the http message body
		if (limitParam != null && limitParam.isEmpty() == false){
			requestMsg.addQuerystringParameter("limit", limitParam);
		}
		if (offsetParam != null && offsetParam.isEmpty() == false){
			requestMsg.addQuerystringParameter("offset", offsetParam);
		}
		
		
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
