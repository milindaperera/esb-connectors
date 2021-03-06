/**
 *  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.connector.clevertim;

import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.connector.core.AbstractConnector;

/**
 * ClevertimCRM CreateCase - creates a new Case.
 * 
 * @see https://github.com/clevertim/clevertim-crm-api/blob/master/resources/case.md
 */
public final class CreateCase extends AbstractConnector {
    
    /**
     * Instance variable to hold the MessageContext object passed in via the Synapse template.
     */
    private MessageContext messageContext;
    
    /**
     * Connector method which is executed at the specified point within the corresponding Synapse template
     * within the connector.
     * 
     * @param mc Synapse Message Context.
     * @see org.wso2.carbon.connector.core.AbstractConnector#connect(org.apache.synapse.MessageContext)
     */
    @Override
    public void connect(final MessageContext mc) {
    
        this.messageContext = mc;
        
        String errorMessage = null;
        
        try {
            
            org.apache.axis2.context.MessageContext axis2MC = ((Axis2MessageContext) mc).getAxis2MessageContext();
            JsonUtil.newJsonPayload(axis2MC, getJsonPayload(), true, true);
        } catch (JSONException je) {
            errorMessage = Constants.INVALID_JSON_MSG;
            log.error(errorMessage, je);
            storeErrorResponseStatus(errorMessage, Constants.ERROR_CODE_JSON_EXCEPTION);
            handleException(errorMessage, je, mc);
        }
        
    }
    
    /**
     * Create JSON request for CreateCase.
     * 
     * @return JSON payload.
     * @throws JSONException thrown when parsing JSON String.
     */
    private String getJsonPayload() throws JSONException {
    
        JSONObject jsonPayload = new JSONObject();
        
        String name = (String) messageContext.getProperty(Constants.NAME);
        if (name != null && !name.isEmpty()) {
            jsonPayload.put(Constants.JSONKeys.NAME, name);
        }
        String description = (String) messageContext.getProperty(Constants.DESCRIPTION);
        if (description != null && !description.isEmpty()) {
            jsonPayload.put(Constants.JSONKeys.DESCRIPTION, description);
        }
        String tags = (String) messageContext.getProperty(Constants.TAGS);
        if (tags != null && !tags.isEmpty()) {
            jsonPayload.put(Constants.JSONKeys.TAGS, new JSONArray(tags));
        }
        String leadUser = (String) messageContext.getProperty(Constants.LEAD_USER);
        if (leadUser != null && !leadUser.isEmpty()) {
            jsonPayload.put(Constants.JSONKeys.LEAD_USER, leadUser);
        }
        String customer = (String) messageContext.getProperty(Constants.CUSTOMER);
        if (customer != null && !customer.isEmpty()) {
            jsonPayload.put(Constants.JSONKeys.CUSTOMER, customer);
        }
        String customFields = (String) messageContext.getProperty(Constants.CUSTOM_FIELDS);
        if (customFields != null && !customFields.isEmpty()) {
            
            jsonPayload.put(Constants.JSONKeys.CUSTOM_FIELD, new JSONObject(customFields));
        }
        
        return jsonPayload.toString();
        
    }
    
    /**
     * Add a <strong>Throwable</strong> to a message context, the message from the throwable is embedded as
     * the Synapse contstant ERROR_MESSAGE.
     * 
     * @param message the error message
     * @param errorCode integer type error code to be added to ERROR_CODE Synapse constant
     */
    private void storeErrorResponseStatus(String message, int errorCode) {
    
        this.messageContext.setProperty(SynapseConstants.ERROR_CODE, errorCode);
        this.messageContext.setProperty(SynapseConstants.ERROR_MESSAGE, message);
        this.messageContext.setFaultResponse(true);
    }
    
}
