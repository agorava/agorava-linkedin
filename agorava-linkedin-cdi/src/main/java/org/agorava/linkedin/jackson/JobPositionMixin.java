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

import org.agorava.linkedin.model.CodeAndName;
import org.agorava.linkedin.model.Location;
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
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

/**
 * @author Antoine Sabot-Durand
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class JobPositionMixin {

    @JsonCreator
    JobPositionMixin(@JsonProperty("title") String title, @JsonProperty("location") Location location) {
    }

    @JsonProperty
    CodeAndName experienceLevel;

    @JsonProperty
    @JsonDeserialize(using = CodeAndNameListDeserializer.class)
    List<CodeAndName> industries;

    @JsonProperty
    @JsonDeserialize(using = CodeAndNameListDeserializer.class)
    List<CodeAndName> jobFunctions;

    @JsonProperty
    CodeAndName jobType;

    private static final class CodeAndNameListDeserializer extends JsonDeserializer<List<CodeAndName>> {
        @Override
        public List<CodeAndName> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
                JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDeserializationConfig(ctxt.getConfig());
            jp.setCodec(mapper);
            if (jp.hasCurrentToken()) {
                JsonNode dataNode = jp.readValueAsTree().get("values");
                if (dataNode != null) {
                    return mapper.readValue(dataNode, new TypeReference<List<CodeAndName>>() {
                    });
                }
            }
            return null;
        }
    }

}
