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

import org.agorava.linkedin.LinkedIn;
import org.agorava.oauth.OAuth10aServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: antoine
 * Date: 19/06/13
 * Time: 18:41
 * To change this template use File | Settings | File Templates.
 */

@LinkedIn
public class LinkedInOauthService extends OAuth10aServiceImpl {

    private final static Map<String, String> REQUEST_HEADER = new HashMap<String, String>() {
        {
            put("Content-Type", "application/json");
            put("x-li-format", "json");

        }
    };


    public LinkedInOauthService() {
        super();
        setRequestHeader(REQUEST_HEADER);
    }
}
