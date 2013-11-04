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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.agorava.linkedin.model.Group;
import org.agorava.linkedin.model.Group.MembershipState;
import org.agorava.linkedin.model.GroupSettings.EmailDigestFrequency;

import java.io.IOException;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GroupSettingsMixin extends LinkedInObjectMixin {

    @JsonCreator
    GroupSettingsMixin(
            @JsonProperty("allowMessagesFromMembers") Boolean allowMessagesFromMembers,
            @JsonProperty("emailAnnouncementsFromManagers") Boolean emailAnnouncementsFromManagers,
            @JsonProperty("emailDigestFrequency") @JsonDeserialize(using = EmailDigestFrequencyDeserializer.class)
            EmailDigestFrequency emailDigestFrequency,
            @JsonProperty("emailForEveryNewPost") Boolean emailForEveryNewPost,
            @JsonProperty("group") Group group,
            @JsonProperty("membershipState") @JsonDeserialize(using = MembershipStateDeserializer.class) MembershipState
                    membershipState,
            @JsonProperty("showGroupLogoInProfile") Boolean showGroupLogoInProfile) {
    }

    private static final class EmailDigestFrequencyDeserializer extends JsonDeserializer<EmailDigestFrequency> {
        @Override
        public EmailDigestFrequency deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            JsonNode node = jp.readValueAsTree();
            return EmailDigestFrequency.valueOf(node.get("code").textValue().replace('-', '_').toUpperCase());
        }
    }

    private static final class MembershipStateDeserializer extends JsonDeserializer<MembershipState> {
        @Override
        public MembershipState deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            JsonNode node = jp.readValueAsTree();
            return MembershipState.valueOf(node.get("code").textValue().replace('-', '_').toUpperCase());
        }
    }

}
