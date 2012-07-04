/*
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
 */

package org.agorava.linkedin.jackson;

import java.util.Date;

import org.agorava.linkedin.model.Company;
import org.agorava.linkedin.model.JobPosition;
import org.agorava.linkedin.model.LinkedInDate;
import org.agorava.linkedin.model.LinkedInProfile;
import org.agorava.linkedin.model.UrlResource;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class JobMixin {

    @JsonCreator
    JobMixin(@JsonProperty("company") Company company, @JsonProperty("description") String description,
             @JsonProperty("id") int id, @JsonProperty("locationDescription") String locationDescription,
             @JsonProperty("siteJobRequest") UrlResource siteJobRequest) {
    }

    @JsonProperty
    boolean active;

    @JsonProperty
    String customerJobCode;

    @JsonProperty
    String descriptionSnippet;

    @JsonProperty
    LinkedInDate expirationDate;

    @JsonProperty
    Date expirationTimestamp;

    @JsonProperty
    LinkedInProfile jobPoster;

    @JsonProperty
    JobPosition position;

    @JsonProperty
    LinkedInDate postingDate;

    @JsonProperty
    Date postingTimestamp;

    @JsonProperty
    String salary;

    @JsonProperty
    String siteJobUrl;

    @JsonProperty
    String skillsAndExperience;

}
