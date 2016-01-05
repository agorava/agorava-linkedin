/*
 * Copyright 2016 Agorava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.agorava.linkedin;

import org.agorava.AgoravaConstants;
import org.agorava.api.oauth.application.OAuthAppSettings;
import org.agorava.api.service.OAuthEncoder;
import org.agorava.api.service.Preconditions;
import org.agorava.spi.ProviderConfigOauth20Final;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author antoine
 * @author keilw
 */

@LinkedIn
public class LinkedInApi extends ProviderConfigOauth20Final {

    private final static String MEDIA_NAME = "LinkedIn";

    private static final String AUTHORIZE_URL = "https://www.linkedin.com/uas/oauth2/authorization?" 
       + "response_type=code&"
       + "state=DCEeFWf45A53sdfKef424&"
       + AgoravaConstants.CLIENT_ID + "=%s&"
       + AgoravaConstants.CLIENT_SECRET + "=%s&"
       + AgoravaConstants.REDIRECT_URI + "=%s";
    	//"https://api.linkedin.com/uas/oauth/authenticate?oauth_token=%s";

    //private static final String REQUEST_TOKEN_URL = "https://api.linkedin.com/uas/oauth/requestToken";

    public LinkedInApi() {
        Collections.emptySet();
    }

    public LinkedInApi(Set<String> scopes) {
        Collections.unmodifiableSet(scopes);
    }

    public static LinkedInApi withScopes(String... scopes) {
        Set<String> scopeSet = new HashSet<String>(Arrays.asList(scopes));
        return new LinkedInApi(scopeSet);
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.linkedin.com/uas/oauth2/accessToken";
    }

    @Override
    public String getAuthorizationUrl(OAuthAppSettings config) {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. LinkedIn does not support OOB");

        // Append scope if present
  /*      if (config.hasScope()) {
            return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope()));
        } else { */
        String url = String.format(AUTHORIZE_URL, config.getApiKey(), config.getApiSecret(), 
        		//"http://localhost:8080/agorava-socializer/home.jsf"); 
        		OAuthEncoder.encode(config.getCallback())); // TODO make this more flexible or get from request
        //System.out.println("URL: " + url);
            return url;
            
        //}
    }

    /*
    private String scopesAsString() {
        StringBuilder builder = new StringBuilder();
        for (String scope : scopes) {
            builder.append("+" + scope);
        }
        return builder.substring(1);
    }
*/

    @Override
    public String getProviderName() {
        return MEDIA_NAME;
    }

}
