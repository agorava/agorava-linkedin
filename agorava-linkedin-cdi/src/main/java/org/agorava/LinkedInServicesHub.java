/*******************************************************************************
 * Copyright 2012 Agorava
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
 ******************************************************************************/


package org.agorava;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.agorava.core.api.event.OAuthComplete;
import org.agorava.core.api.event.SocialEvent.Status;
import org.agorava.core.api.oauth.OAuthService;
import org.agorava.core.cdi.AbstractSocialNetworkServicesHub;
import org.agorava.linkedin.impl.ProfileServiceImpl;
import org.jboss.solder.logging.Logger;

/**
 * @author antoine
 * 
 */
public class LinkedInServicesHub extends AbstractSocialNetworkServicesHub {

    private final static Map<String, String> REQUEST_HEADER = new HashMap<String, String>() {
        {
            put("Content-Type", "application/json");
            put("x-li-format", "json");

        }
    };

    @Inject
    Logger log;

    @Inject
    Instance<LinkedInBaseService> services;

    @Override
    public void configureService(OAuthService service) {
        super.configureService(service);

        service.setRequestHeader(REQUEST_HEADER);
    }

    @Override
    public Annotation getQualifier() {
        return LinkedInLiteral.INSTANCE;
    }

    public void initMyProfile(@Observes @LinkedIn OAuthComplete oauthComplete) {
        log.debug("Initializing LinkedIn profile");
        if (oauthComplete.getStatus() == Status.SUCCESS)
            oauthComplete.getEventData().setUserProfile(services.select(ProfileServiceImpl.class).get().getUserProfile());

    }
}
