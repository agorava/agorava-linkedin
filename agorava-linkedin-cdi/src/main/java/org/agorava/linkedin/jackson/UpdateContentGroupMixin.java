/*
 * Copyright 2014 Agorava
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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.agorava.api.atinject.BeanResolver;
import org.agorava.linkedin.model.MemberGroup;
import org.agorava.linkedin.model.UrlResource;

import java.io.IOException;
import java.util.List;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class UpdateContentGroupMixin extends LinkedInObjectMixin {

    @JsonProperty("memberGroups")
    @JsonDeserialize(using = MemberGroupsListDeserializer.class)
    List<MemberGroup> memberGroups;

    @JsonCreator
    UpdateContentGroupMixin(@JsonProperty("id") String id, @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName, @JsonProperty("email-address") String email,
            @JsonProperty("headline") String headline,
            @JsonProperty("industry") String industry, @JsonProperty("publicProfileUrl") String
            publicProfileUrl,
            @JsonProperty("siteStandardProfileRequest") UrlResource siteStandardProfileRequest,
            @JsonProperty("pictureUrl") String profilePictureUrl) {
    }

    private static class MemberGroupsListDeserializer extends JsonDeserializer<List<MemberGroup>> {
        @SuppressWarnings("unchecked")
        @Override
        public List<MemberGroup> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            ObjectMapper mapper = BeanResolver.getInstance().resolve(ObjectMapper.class);
            jp.setCodec(mapper);
            if (jp.hasCurrentToken()) {
                JsonNode dataNode = jp.readValueAs(JsonNode.class).get("values");
                return (List<MemberGroup>) mapper.reader(new TypeReference<List<MemberGroup>>() {
                }).readValue(dataNode);
            }

            return null;
        }
    }

}
