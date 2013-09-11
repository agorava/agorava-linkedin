/*
 * Copyright 2013 Agorava
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

import org.agorava.core.api.oauth.Token;
import org.agorava.core.spi.TierConfigOauth10a;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * @author antoine
 */

@LinkedIn
public class LinkedInApi extends TierConfigOauth10a {

    private final static String MEDIA_NAME = "LinkedIn";
    private static final String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authenticate?oauth_token=%s";
    private static final String REQUEST_TOKEN_URL = "https://api.linkedin.com/uas/oauth/requestToken";
    private final Set<String> scopes;

    public LinkedInApi() {
        scopes = Collections.emptySet();
    }

    public LinkedInApi(Set<String> scopes) {
        this.scopes = Collections.unmodifiableSet(scopes);
    }

    public static LinkedInApi withScopes(String... scopes) {
        Set<String> scopeSet = new HashSet<String>(Arrays.asList(scopes));
        return new LinkedInApi(scopeSet);
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.linkedin.com/uas/oauth/accessToken";
    }

    @Override
    public String getRequestTokenEndpoint() {
        return scopes.isEmpty() ? REQUEST_TOKEN_URL : REQUEST_TOKEN_URL + "?scope=" + scopesAsString();
    }

    private String scopesAsString() {
        StringBuilder builder = new StringBuilder();
        for (String scope : scopes) {
            builder.append("+" + scope);
        }
        return builder.substring(1);
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }

    @Override
    public String getServiceName() {
        return MEDIA_NAME;
    }

}
