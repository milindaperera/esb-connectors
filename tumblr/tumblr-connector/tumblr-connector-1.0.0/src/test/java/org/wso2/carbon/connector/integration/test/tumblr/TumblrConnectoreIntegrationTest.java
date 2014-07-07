/**
 *  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.integration.test.tumblr;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;




public class TumblrConnectoreIntegrationTest extends ConnectorIntegrationTestBase {

    protected static final String CONNECTOR_NAME = "tumblr-connector-1.0.0";
    
    private static Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();
    
    private static String createdTextPostID;
    private static String connecterCreatedTextPostID;
    
    //private static String reblogKey;
    //private static String connectorReblogKey;
    
    @BeforeTest(alwaysRun = true)
    protected void init() throws Exception {
        super.init(CONNECTOR_NAME);

        esbRequestHeadersMap.put("Content-Type", "application/json");
       
    }


    @Override
    protected void cleanup() {
        axis2Client.destroy();
    }
    
    
/*************************************************************************************************
 * 
 * 										POSTIVE TEST CASES
 * 
 * ************************************************************************************************/    

    /**
     * Positive test case for getBlogInfo
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {getBlogInfo} integration positive test")
    public void testTumblrGetBlogInfo() throws JSONException, Exception {

    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/info?api_key=" + consumerKey;
        System.out.println("requestUrl : " + requestUrl);
        
        String directResponse = ConnectorIntegrationUtil.DirectHttpGET(requestUrl);
        System.out.println("DirectResponse : " +directResponse);
        
        JSONObject directResponseJObj = new JSONObject(directResponse);
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getBlogInfo.json");
        
         
        //String connectorResponse = ConnectorIntegrationUtil.ConnectorHttpPOST("", getProxyServiceURL("tumblr"));
        System.out.println("Connector response : " +esbRestResponse.getBody().toString());
        
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), directResponseJObj.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), directResponseJObj.get("response").toString());
    }

    
    
    
    
    /**
     * Positive integration test case for getBlogLikes
     * Including default values for optional parameters 
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {getBlogLikes} integration positive test")
    public void testTumblrGetBlogLikes() throws JSONException, Exception {

    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/likes?api_key=" + consumerKey + "&limit=20&offset=0";
        System.out.println("requestUrl : " + requestUrl);
        
        String directResponse = ConnectorIntegrationUtil.DirectHttpGET(requestUrl);
        System.out.println("DirectResponse : " +directResponse);
        
        JSONObject directResponseJObj = new JSONObject(directResponse);
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getBlogLikes.json");
        
         
        //String connectorResponse = ConnectorIntegrationUtil.ConnectorHttpPOST("", getProxyServiceURL("tumblr"));
        System.out.println("Connector response : " +esbRestResponse.getBody().toString());
        
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), directResponseJObj.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), directResponseJObj.get("response").toString());
    }
    
    
    
    
    
    
    /**
     * Positive integration test case for getAvatar 
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {getAvatar} integration positive test")
    public void testTumblrGetAvatar() throws JSONException, Exception {

    	// TODO : Remove sysout prints
                
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/avatar";
        System.out.println("requestUrl : " + requestUrl);
        
        String directResponse = ConnectorIntegrationUtil.DirectHttpGET(requestUrl);
        System.out.println("DirectResponse : " +directResponse);
        
        
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));

        String esbResponse = ConnectorIntegrationUtil.ConnectorHttpPOST("{\"json_request\":{\"operation\":\"getAvatar\",\"baseHostUrl\":\""
        																	+ targetBlogUrl
        																	+"\"}}", getProxyServiceURL("tumblr"));
        
        System.out.println("Connector response:" +esbResponse);
 
        Assert.assertEquals(esbResponse, directResponse);
    }
    
    
    
    
    
    
    
    /**
     * Positive integration test case for getFollowers
     * Including default values for optional parameters 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {getFollowers} integration positive test")
    public void testTumblrGetFollowers() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/followers";
        System.out.println("requestUrl : " + requestUrl);
        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("limit", "20");
        params.put("offset", "0");
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthGET(requestUrl, consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, params);
        
        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getFollowers.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
        														directResponse.get("response").toString());
        
    }
    
    
    
    /**
     * Positive integration test case for getPosts
     * Use only mandatory parameters
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {getPosts} integration positive test")
    public void testTumblrGetPosts() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/posts?api_key=" + consumerKey;
        System.out.println("requestUrl : " + requestUrl);
        
        
        String directResponse = ConnectorIntegrationUtil.DirectHttpGET(requestUrl);
        
        //System.out.println("DirectResponse : " +directResponse);
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        
        JSONObject directResponseJObj = new JSONObject(directResponse);

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getPosts.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        //System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponseJObj.get("meta").toString());
        
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
        														directResponseJObj.get("response").toString());
        
    }
    
    
    
    
    /**
     * Positive integration test case for getQueuedPosts
     * Use only mandatory parameters
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {getPosts} integration positive test")
    public void testTumblrGetQueuedPosts() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/posts/queue";
        System.out.println("requestUrl : " + requestUrl);
        
        HashMap<String, String> params = new HashMap<String, String>();

        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthGET(requestUrl, consumerKey, 
        																consumerSecret, accessToken, 
        																tokenSecret, params);
        
        System.out.println("DirectResponse : " +directResponse);
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getQueuedPosts.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
        														directResponse.get("response").toString());
        
    }
    
    
    
    
    
    
    /**
     * Positive integration test case for getDraftedPosts
     * Use only mandatory parameters
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {getDraftedPosts} integration positive test")
    public void testTumblrGetDraftedPosts() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/posts/draft";
        System.out.println("requestUrl : " + requestUrl);
        
        HashMap<String, String> params = new HashMap<String, String>();

        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthGET(requestUrl, consumerKey, 
        																consumerSecret, accessToken, 
        																tokenSecret, params);
        
        //System.out.println("DirectResponse : " +directResponse);
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getDraftedPosts.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        //System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
        														directResponse.get("response").toString());
        
    }
    
    
    
    
    /**
     * Positive integration test case for createPost (text post)
     * mandatory parameters: type="text", body="This is INTEGRATION TEST text post" + [method of posting]
     * Add default values (according to tumblr api) for some optional parameters: state=publishd, format=html 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {createPost} text post integration positive test")
    public void testTumblrCreateTextPost() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/post";
        System.out.println("requestUrl : " + requestUrl);
        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("state", "published");
        params.put("format", "html");
        params.put("type", "text");
        params.put("body", "This is INTEGRATION TEST text post : DIRECT TEST CASE");
        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																	consumerSecret, accessToken, 
        																	tokenSecret, params);
        createdTextPostID = directResponse.getJSONObject("response").get("id").toString();
        
        
        System.out.println("direct post ID: " +createdTextPostID);
        
        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "createTextPost.json");
        
        //store id for later use in deletePost test
        connecterCreatedTextPostID = esbRestResponse.getBody().getJSONObject("response").get("id").toString();
        
        System.out.println("connector post ID: " +connecterCreatedTextPostID);
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        
    }
    
    
    
    
    
    
    /**
     * Positive integration test case for editPost (text post)
     * mandatory parameters: id, type="text", body="This is INTEGRATION TEST text post" + [method of posting]
     * Add default values (according to tumblr api) for some optional parameters: state=publishd, format=html 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "tumblr {editPost} text post integration positive test")
    public void testTumblrEditTextPost() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Edit Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/post/edit";
        System.out.println("requestUrl : " + requestUrl);
        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("state", "published");
        params.put("format", "html");
        params.put("type", "text");
        params.put("id", createdTextPostID);
        params.put("body", "This is INTEGRATION TEST text post : EDIT DIRECTLY");
        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																	consumerSecret, accessToken, 
        																	tokenSecret, params);
        
        
        
        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        
        String jsonRequestBody = "{\"json_request\":{\"operation\":\"editPost\","
        											+ "\"consumerKey\":\"" 		+ consumerKey    +"\","
        											+ "\"consumerSecret\":\"" 	+ consumerSecret +"\","
        											+ "\"accessToken\":\"" 		+ accessToken	 +"\","
        											+ "\"tokenSecret\":\"" 		+tokenSecret 	 +"\","
        											+ "\"baseHostUrl\":\"" 		+targetBlogUrl 	 +"\","
        											+ "\"postId\":\""           +connecterCreatedTextPostID +"\","
        											+ "\"postType\":\"text\","
        											+ "\"postBody\":\"This is INTEGRATION TEST text post : EDITED THROUGH ESB\"}}";

        JSONObject esbRestResponse = ConnectorIntegrationUtil.ConnectorHttpPOSTJsonObj(jsonRequestBody, 
        																				getProxyServiceURL("tumblr"));
        
        System.out.println("ESB:" +esbRestResponse);
       
        Assert.assertEquals(directResponse.get("meta").toString(), 	"{\"status\":200,\"msg\":\"OK\"}");
        Assert.assertEquals(esbRestResponse.get("meta").toString(), "{\"status\":200,\"msg\":\"OK\"}");
        //Assert.assertEquals(esbRestResponse.get("response"), "{\"id\":" +connecterCreatedTextPostID +"}");
    }
    
    
    
    
    
    /**
     * Positive integration test case for reblogPost (text post)
     * mandatory parameters: id, type="text", body="This is INTEGRATION TEST text post" + [method of posting]
     * Add default values (according to tumblr api) for some optional parameters: state=publishd, format=html 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 2, groups = {"wso2.esb"}, description = "tumblr {reblogPost} text post integration positive test")
    public void testTumblrReblogTextPost() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        String reblogKey = connectorProperties.getProperty("reblogKey");
        String reblogPostId = connectorProperties.getProperty("reblogPostId");
        
        //Edit Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/post/reblog";
        System.out.println("requestUrl : " + requestUrl);
        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("state", "published");
        params.put("format", "html");
        params.put("type", "text");
        params.put("id", reblogPostId);
        params.put("reblog_key", reblogKey);
        params.put("comment", "This is INTEGRATION TEST text post : REBLOG DIRECTLY");
        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																	consumerSecret, accessToken, 
        																	tokenSecret, params);
        
        
        
        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        
        String jsonRequestBody = "{\"json_request\":{\"operation\":\"reblogPost\","
        											+ "\"consumerKey\":\"" 		+ consumerKey    +"\","
        											+ "\"consumerSecret\":\"" 	+ consumerSecret +"\","
        											+ "\"accessToken\":\"" 		+ accessToken	 +"\","
        											+ "\"tokenSecret\":\"" 		+tokenSecret 	 +"\","
        											+ "\"baseHostUrl\":\"" 		+targetBlogUrl 	 +"\","
        											+ "\"postId\":\""           +reblogPostId 	 +"\","
        											+ "\"reblogKey\":\""        +reblogKey 		 +"\","
        											+ "\"	\":\"text\","
        											+ "\"postComment\":\"This is INTEGRATION TEST text post : REBLOG THROUGH ESB\"}}";

        JSONObject esbRestResponse = ConnectorIntegrationUtil.ConnectorHttpPOSTJsonObj(jsonRequestBody, 
        																				getProxyServiceURL("tumblr"));
        
        System.out.println("ESB:" +esbRestResponse);
       
        Assert.assertEquals(directResponse.get("meta").toString(), 	"{\"status\":201,\"msg\":\"Created\"}");
        Assert.assertEquals(esbRestResponse.get("meta").toString(), "{\"status\":201,\"msg\":\"Created\"}");
    }
    
    
    
    
    
    /**
     * Positive integration test case for deletePost (text post)
     * mandatory parameters: post id 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {deletePost} text post integration positive test")
    public void testTumblrDeletePost() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/post/delete";
        System.out.println("requestUrl : " + requestUrl);
        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("id", createdTextPostID);
        
        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																	consumerSecret, accessToken, 
        																	tokenSecret, params);
        
        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        String esbResponse = ConnectorIntegrationUtil.ConnectorHttpPOST("{\"json_request\":{\"operation\":\"deletePost\",\"consumerKey\":\""+consumerKey+"\","
																			    +"\"consumerSecret\":\""+consumerSecret +"\","
																			    +"\"accessToken\":\"" 	+accessToken  +"\","
																			    +"\"tokenSecret\":\"" 	+tokenSecret  +"\","
																			    +"\"baseHostUrl\":\"" 	+targetBlogUrl +"\","
																			    +"\"postId\":\""		+connecterCreatedTextPostID +"\"}}", 
																		getProxyServiceURL("tumblr"));
        
        
        System.out.println("ESB:" +esbResponse);
        
        Assert.assertEquals(directResponse.get("meta").toString(), "{\"status\":200,\"msg\":\"OK\"}");
        Assert.assertEquals(esbResponse, "{\"meta\":{\"status\":200,\"msg\":\"OK\"},\"response\":{\"id\":" +connecterCreatedTextPostID +"}}");
    }
    
    
    
    
    
    /**
     * Positive integration test case for getUserInfo 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {getUserInfo} integration positive test")
    public void testTumblrGetUserInfo() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        
        requestUrl = "http://api.tumblr.com/v2/user/info";
        System.out.println("requestUrl : " + requestUrl);        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthGET(requestUrl, 		consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, 	null);        

        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getUserInfo.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
																directResponse.get("response").toString());
    }
    
    /**
     * Positive integration test case for getUserDashboard 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {getUserDashboard} integration positive test")
    public void testTumblrGetUserDashboard() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        
        requestUrl = "http://api.tumblr.com/v2/user/dashboard";
        System.out.println("requestUrl : " + requestUrl);        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthGET(requestUrl, 		consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, 	null);        

        //System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getUserDashboard.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        //System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
																directResponse.get("response").toString());
    }
    
    
    
    
    
    /**
     * Positive integration test case for getLikes 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {getLikes} integration positive test")
    public void testTumblrGetLikes() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        
        requestUrl = "http://api.tumblr.com/v2/user/likes";
        System.out.println("requestUrl : " + requestUrl);        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthGET(requestUrl, 		consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, 	null);        

        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getLikes.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
																directResponse.get("response").toString());
    }
    
    
    
    /**
     * Positive integration test case for getFollowing 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {getFollowing} integration positive test")
    public void testTumblrGetFollowing() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        
        requestUrl = "http://api.tumblr.com/v2/user/following";
        System.out.println("requestUrl : " + requestUrl);        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthGET(requestUrl, 		consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, 	null);        

        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getFollowing.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
																directResponse.get("response").toString());
    }
    
    
    
    
    
    
    /**
     * Positive integration test case for follow 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {follow} integration positive test")
    public void testTumblrFollow() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        String followBlogUrl = connectorProperties.getProperty("followBlogUrl");
        
        //Get Direct response from tumblr        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("url", followBlogUrl);
        
        String requestUrl = "http://api.tumblr.com/v2/user/follow";
        System.out.println("requestUrl : " + requestUrl);        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, params);       

        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "follow.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
																directResponse.get("response").toString());
    }
    
    
    
    
    /**
     * Positive integration test case for unfollow 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {unfollow} integration positive test")
    public void testTumblrUnFollow() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        String followBlogUrl = connectorProperties.getProperty("followBlogUrl");
        
        //Get Direct response from tumblr        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("url", followBlogUrl);
        
        String requestUrl = "http://api.tumblr.com/v2/user/unfollow";
        System.out.println("requestUrl : " + requestUrl);        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, params);       

        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "unFollow.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
																directResponse.get("response").toString());
    }
    
    
    
    
    
    /**
     * Positive integration test case for like 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {like} integration positive test")
    public void testTumblrLike() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        String directReblogKey = connectorProperties.getProperty("directLikeReblogKey");
        String directPostId = connectorProperties.getProperty("directLikePostId");
        
        //Get Direct response from tumblr        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("id", directPostId);
        params.put("reblog_key", directReblogKey);
        
        String requestUrl = "http://api.tumblr.com/v2/user/like";
        System.out.println("requestUrl : " + requestUrl);        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, params);       

        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "like.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
																directResponse.get("response").toString());
    }
    
    
    
    
    
    
    /**
     * Positive integration test case for unLike 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 3, groups = {"wso2.esb"}, description = "tumblr {like} integration positive test")
    public void testTumblrUnLike() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        String reblogKey = connectorProperties.getProperty("directLikeReblogKey");
        String postId = connectorProperties.getProperty("directLikePostId");
        
        //Get Direct response from tumblr        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("id", postId);
        params.put("reblog_key", reblogKey);
        
        String requestUrl = "http://api.tumblr.com/v2/user/unlike";
        System.out.println("requestUrl : " + requestUrl);        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																		consumerSecret, accessToken, 
        																		tokenSecret, params);       

        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "unLike.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
																directResponse.get("response").toString());
    }

    
    
    
    
    /**
     * Positive integration test case for getTaggedPosts
     * Use only mandatory parameters
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 4, groups = {"wso2.esb"}, description = "tumblr {getTaggedPosts} integration positive test")
    public void testTumblrGetTaggedPosts() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        
        String tag = connectorProperties.getProperty("postTag");
        
        //Get Direct response from tumblr
        String requestUrl = "http://api.tumblr.com/v2/tagged?api_key=" + consumerKey +"&tag=" + tag;
        System.out.println("requestUrl : " + requestUrl);
                
        String directResponse = ConnectorIntegrationUtil.DirectHttpGET(requestUrl);
        
        //System.out.println("DirectResponse : " +directResponse);
        
        //Get response using the connector
        //System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        
        JSONObject directResponseJObj = new JSONObject(directResponse);

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "getTaggedPosts.json");
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        //System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponseJObj.get("meta").toString());
        
        Assert.assertEquals(esbRestResponse.getBody().get("response").toString(), 
        														directResponseJObj.get("response").toString());
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*************************************************************************************************
     * 
     * 										NEGATIVE TEST CASES
     * 
     * ************************************************************************************************/   
    
    
    
    /**
     * Negative integration test case for createPost (text post)
     * mandatory parameters: type="text", body="This is INTEGRATION TEST text post" + [method of posting]
     * Add default values (according to tumblr api) for some optional parameters: state=publishd, format=html 
     * OAuth 1.0a authentcation required
     * @throws JSONException 
     * @throws IOException 
     */
    @Test(priority = 5, groups = {"wso2.esb"}, description = "tumblr {createPost} text post integration negative test")
    public void NegativeTestTumblrCreateTextPost() throws IOException, JSONException{
    	
    	// TODO : Remove sysout prints
        String consumerKey = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_KEY);
        System.out.println("consumerKey : " + consumerKey);
        String consumerSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_CONSUMER_SECRET);
        System.out.println("consumerSecret : " + consumerSecret);
        
        String accessToken = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_ACCESS_TOKEN);
        System.out.println("accessToken : " + accessToken);
        String tokenSecret = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_TOKEN_SECRET);
        System.out.println("tokenSecret : " + tokenSecret);
        
        //Get Direct response from tumblr
        String requestUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BASEAPIURL);
        String targetBlogUrl = connectorProperties.getProperty(TumblrTestConstants.PROPERTY_BLOGURL);
        
        requestUrl = requestUrl + "/" + targetBlogUrl + "/post";
        System.out.println("requestUrl : " + requestUrl);
        
        HashMap<String, String> params = new HashMap<String, String>();
        
        params.put("type", "");
        params.put("body", "");
        
        
        JSONObject directResponse = ConnectorIntegrationUtil.DirectHttpAuthPOST(requestUrl, consumerKey, 
        																	consumerSecret, accessToken, 
        																	tokenSecret, params);
        //createdTextPostID = directResponse.getJSONObject("response").get("id").toString();
        
        
        //System.out.println("direct post ID: " +createdTextPostID);
        
        System.out.println("DirectResponse : " +directResponse.toString());
        
        //Get response using the connector
        System.out.println("Proxy service url :" + getProxyServiceURL("tumblr"));
        

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL("tumblr"), 
        																"POST", esbRequestHeadersMap,  "negative/createTextPost.json");
        
        //store id for later use in deletePost test
        //connecterCreatedTextPostID = esbRestResponse.getBody().getJSONObject("response").get("id").toString();
        
        //System.out.println("connector post ID: " +connecterCreatedTextPostID);
        
        System.out.println("ESB:" +esbRestResponse.getBody().get("meta").toString());
        System.out.println("ESB:" +esbRestResponse.getBody().get("response").toString());
    	
        Assert.assertEquals(esbRestResponse.getBody().get("meta").toString(), 
        														directResponse.get("meta").toString());
        
    }
    
    
    
    
    
    
    
}