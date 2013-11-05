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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.agorava.api.atinject.BeanResolver;
import org.agorava.linkedin.model.Group.GroupAvailableAction;
import org.agorava.linkedin.model.Group.MembershipState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GroupRelationMixin extends LinkedInObjectMixin {

    @JsonCreator
    GroupRelationMixin(
            @JsonProperty("availableActions") @JsonDeserialize(using = AvailableActionDeserializer.class)
            List<GroupAvailableAction> availableActions,
            @JsonProperty("membershipState") @JsonDeserialize(using = MembershipStateDeserializer.class) MembershipState
                    membershipState) {
    }

    private static final class AvailableActionDeserializer extends JsonDeserializer<List<GroupAvailableAction>> {
        public List<GroupAvailableAction> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            ObjectMapper mapper = BeanResolver.getInstance().resolve(ObjectMapper.class);
            jp.setCodec(mapper);
            List<GroupAvailableAction> actions = new ArrayList<GroupAvailableAction>();
            if (jp.hasCurrentToken()) {
                JsonNode dataNode = jp.readValueAs(JsonNode.class).get("values");
                if (dataNode != null) {
                    for (JsonNode d : dataNode) {
                        String s = d.path("code").textValue();
                        if (s != null) {
                            actions.add(GroupAvailableAction.valueOf(s.replace('-', '_').toUpperCase()));
                        }
                    }
                }
            }
            return actions;
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
