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

import org.agorava.linkedin.model.Group;
import org.agorava.linkedin.model.Group.MembershipState;
import org.agorava.linkedin.model.GroupSettings.EmailDigestFrequency;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.IOException;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GroupSettingsMixin {

    @JsonCreator
    GroupSettingsMixin(
            @JsonProperty("allowMessagesFromMembers") Boolean allowMessagesFromMembers,
            @JsonProperty("emailAnnouncementsFromManagers") Boolean emailAnnouncementsFromManagers,
            @JsonProperty("emailDigestFrequency") @JsonDeserialize(using = EmailDigestFrequencyDeserializer.class) EmailDigestFrequency emailDigestFrequency,
            @JsonProperty("emailForEveryNewPost") Boolean emailForEveryNewPost,
            @JsonProperty("group") Group group,
            @JsonProperty("membershipState") @JsonDeserialize(using = MembershipStateDeserializer.class) MembershipState membershipState,
            @JsonProperty("showGroupLogoInProfile") Boolean showGroupLogoInProfile) {
    }

    private static final class EmailDigestFrequencyDeserializer extends JsonDeserializer<EmailDigestFrequency> {
        @Override
        public EmailDigestFrequency deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            JsonNode node = jp.readValueAsTree();
            return EmailDigestFrequency.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
        }
    }

    private static final class MembershipStateDeserializer extends JsonDeserializer<MembershipState> {
        @Override
        public MembershipState deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            JsonNode node = jp.readValueAsTree();
            return MembershipState.valueOf(node.get("code").getTextValue().replace('-', '_').toUpperCase());
        }
    }

}
