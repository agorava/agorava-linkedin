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
/**
 *
 */
package org.agorava;

import org.agorava.api.oauth.OAuthService;
import org.agorava.linkedin.LinkedIn;
import org.agorava.spi.ProvideApiService;
import org.apache.commons.beanutils.BeanMap;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author Antoine Sabot-Durand
 */
public abstract class LinkedInBaseService extends ProvideApiService {

    private static String API_ROOT = "https://api.linkedin.com/v1/";

    protected static final String BASE_URL = API_ROOT + "people/";

    @Inject
    @LinkedIn
    private OAuthService service;


    public String buildUri(String url, Object pojo) {
        Map beanMap = new BeanMap(pojo);
        return params.addMap(beanMap).asUrl(url);
    }

    @Override
    public OAuthService getService() {
        return service;
    }

    @Override
    public String buildAbsoluteUri(String uri) {
        return API_ROOT + uri;
    }
}
