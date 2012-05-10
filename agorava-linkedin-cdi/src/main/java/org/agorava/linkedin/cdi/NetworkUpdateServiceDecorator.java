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
/**
 * 
 */
package org.agorava.linkedin.cdi;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.agorava.LinkedIn;
import org.agorava.core.api.event.SocialEvent.Status;
import org.agorava.core.api.event.StatusUpdated;
import org.agorava.linkedin.NetworkUpdateService;
import org.agorava.linkedin.model.NewShare;

@Decorator
/**
 * @author Antoine Sabot-Durand
 *
 */
public abstract class NetworkUpdateServiceDecorator implements NetworkUpdateService {

    /**
     * 
     */
    @Inject
    @Delegate
    @Any
    private NetworkUpdateService delegate;

    @Inject
    @LinkedIn
    private Event<StatusUpdated> statusUpdateEventProducer;

    @Override
    public String share(NewShare share) {
        String res = delegate.share(share);
        statusUpdateEventProducer.fire(new StatusUpdated(Status.SUCCESS, share.getComment(), res));
        return res;
    }

}
