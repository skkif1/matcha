package com.matcha.entity.jsonDeserialize;

import com.matcha.entity.Message;
import com.matcha.entity.SearchRequest;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class SearcRequestDeserializer extends JsonDeserializer{

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Integer message = node.get("minAge").asInt();
        SearchRequest request = new SearchRequest();
        return null;
    }
}
