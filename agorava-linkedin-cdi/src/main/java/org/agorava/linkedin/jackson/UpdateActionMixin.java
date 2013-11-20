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
import org.agorava.api.atinject.BeanResolver;
import org.agorava.linkedin.model.Comment;
import org.agorava.linkedin.model.LinkedInProfile;
import org.agorava.linkedin.model.UpdateContent;
import org.agorava.linkedin.model.UpdateContentCompany;
import org.agorava.linkedin.model.UpdateContentShare;
import org.agorava.linkedin.model.UpdateType;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class UpdateActionMixin extends LinkedInObjectMixin {

    @JsonCreator
    UpdateActionMixin(@JsonProperty("timestamp") Date timestamp, @JsonProperty("updateKey") String updateKey,
                      @JsonProperty("updateType") @JsonDeserialize(using = UpdateTypeDeserializer.class) UpdateType
                              updateType) {
    }

    @JsonProperty("isCommentable")
    boolean commentable;

    @JsonProperty("isLikable")
    boolean likeable;

    @JsonProperty("isLiked")
    boolean liked;

    @JsonProperty("numLikes")
    int numLikes;

    @JsonProperty("likes")
    @JsonDeserialize(using = LikesListDeserializer.class)
    List<LinkedInProfile> likes;

    @JsonProperty("updateComments")
    @JsonDeserialize(using = CommentsListDeserializer.class)
    List<Comment> updateComments;

    @JsonProperty("updateContent")
    @JsonDeserialize(using = UpdateContentDeserializer.class)
    UpdateContent updateContent;

    private static class CommentsListDeserializer extends JsonDeserializer<List<Comment>> {
        public List<Comment> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            ObjectMapper mapper = BeanResolver.getInstance().resolve(ObjectMapper.class);
            jp.setCodec(mapper);
            if (jp.hasCurrentToken()) {
                JsonNode dataNode = jp.readValueAs(JsonNode.class).get("values");
                if (dataNode != null) {
                    return mapper.reader(new TypeReference<List<Comment>>() {
                    }).readValue(dataNode);
                }
            }
            return null;
        }
    }

    private static class UpdateContentDeserializer extends JsonDeserializer<UpdateContent> {
        @Override
        public UpdateContent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            ObjectMapper mapper = BeanResolver.getInstance().resolve(ObjectMapper.class);
            jp.setCodec(mapper);

            JsonNode content = jp.readValueAs(JsonNode.class);
            JsonNode person = content.get("person");
            JsonNode company = content.get("company");
            // person for a SHAR update
            if (person != null) {
                return mapper.reader(new TypeReference<UpdateContentShare>() {
                }).readValue(person);
            }
            // company and companyStatusUpdate for CMPY update
            else if (company != null) {
                return mapper.reader(new TypeReference<UpdateContentCompany>() {
                }).readValue(content);
            }
            return null;
        }

    }

}
