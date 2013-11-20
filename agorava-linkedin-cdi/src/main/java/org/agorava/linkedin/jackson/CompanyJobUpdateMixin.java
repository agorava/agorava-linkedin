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

package org.agorava.linkedin.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.agorava.linkedin.model.Job;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class CompanyJobUpdateMixin extends LinkedInObjectMixin {

    @JsonCreator
    CompanyJobUpdateMixin(@JsonProperty("action") @JsonDeserialize(using = CodeDeserializer.class) String action,
                          @JsonProperty("job") Job job) {
    }

}
