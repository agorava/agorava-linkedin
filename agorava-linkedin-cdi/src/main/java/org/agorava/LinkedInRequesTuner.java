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

package org.agorava;

import org.agorava.api.rest.Request;
import org.agorava.api.rest.RequestTuner;
import org.agorava.linkedin.LinkedIn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Antoine Sabot-Durand
 */
@LinkedIn
public class LinkedInRequesTuner implements RequestTuner {

    private final static Map<String, String> REQUEST_HEADER = new HashMap<String, String>() {
            {
                put("Content-Type", "application/json");
                put("x-li-format", "json");

            }
        };

    @Override
    public void tune(Request request) {
        request.getHeaders().putAll(REQUEST_HEADER);

    }
}
