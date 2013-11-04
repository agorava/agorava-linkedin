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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.agorava.linkedin.model.LinkedInProfile;
import org.agorava.linkedin.model.Post.PostRelation;
import org.agorava.linkedin.model.Post.PostType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class PostMixin extends LinkedInObjectMixin {

    @JsonCreator
    PostMixin(@JsonProperty("creator") LinkedInProfile creator, @JsonProperty("id") String id,
              @JsonProperty("title") String title,
              @JsonProperty("type") @JsonDeserialize(using = PostTypeDeserializer.class) PostType type) {
    }

    @JsonProperty
    Date creationTimestamp;

    @JsonProperty
    PostRelation relationToViewer;

    @JsonProperty
    String summary;

    @JsonProperty
    @JsonDeserialize(using = LikesListDeserializer.class)
    List<LinkedInProfile> likes;

    private static final class PostTypeDeserializer extends JsonDeserializer<PostType> {
        @Override
        public PostType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = jp.readValueAsTree();
            return PostType.valueOf(node.get("code").textValue().replace('-', '_').toUpperCase());
        }
    }

    private static final class LikesListDeserializer extends JsonDeserializer<List<LinkedInProfile>> {
        @Override
        public List<LinkedInProfile> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            jp.setCodec(mapper);
            List<LinkedInProfile> likes = new ArrayList<LinkedInProfile>();
            if (jp.hasCurrentToken()) {
                JsonNode dataNode = jp.readValueAs(JsonNode.class).get("values");
                if (dataNode != null) {
                    for (JsonNode d : dataNode) {
                        LinkedInProfile p = mapper.reader(new TypeReference<LinkedInProfile>() {
                        }).readValue(d.path("person"));
                        likes.add(p);
                    }
                }
            }
            return likes;
        }
    }

}
