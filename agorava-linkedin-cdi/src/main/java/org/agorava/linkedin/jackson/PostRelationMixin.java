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

package org.agorava.linkedin.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.agorava.linkedin.model.Group.GroupAvailableAction;
import org.agorava.linkedin.model.Post.PostAvailableAction;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * 
 * @author Antoine Sabot-Durand
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class PostRelationMixin {

    @JsonCreator
    PostRelationMixin(
            @JsonProperty("availableActions") @JsonDeserialize(using = AvailableActionDeserializer.class) List<GroupAvailableAction> availableActions,
            @JsonProperty("isFollowing") Boolean isFollowing, @JsonProperty("isLiked") Boolean isLiked) {
    }

    private static final class AvailableActionDeserializer extends JsonDeserializer<List<PostAvailableAction>> {
        @Override
        public List<PostAvailableAction> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDeserializationConfig(ctxt.getConfig());
            jp.setCodec(mapper);
            List<PostAvailableAction> actions = new ArrayList<PostAvailableAction>();
            if (jp.hasCurrentToken()) {
                JsonNode dataNode = jp.readValueAsTree().get("values");
                if (dataNode != null) {
                    for (JsonNode d : dataNode) {
                        String s = d.path("code").getTextValue();
                        if (s != null) {
                            actions.add(PostAvailableAction.valueOf(s.replace('-', '_').toUpperCase()));
                        }
                    }
                }
            }
            return actions;
        }
    }

}
