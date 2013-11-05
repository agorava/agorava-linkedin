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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.agorava.api.atinject.BeanResolver;
import org.agorava.linkedin.model.ConnectionAuthorization;

import java.io.IOException;

/**
 * @author Antoine Sabot-Durand
 */
final class ConnectionAuthorizationDeserializer extends JsonDeserializer<ConnectionAuthorization> {

    @Override
    public ConnectionAuthorization deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        ObjectMapper mapper = BeanResolver.getInstance().resolve(ObjectMapper.class);
        if (jp.hasCurrentToken() && jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
            JsonNode dataNode = jp.readValueAs(JsonNode.class).get("headers").get("values").get(0);
            if (dataNode != null) {
                return mapper.reader(new TypeReference<ConnectionAuthorization>() {
                }).readValue(dataNode);
            }
        }
        throw ctxt.mappingException("Expected JSON object");
    }

}
