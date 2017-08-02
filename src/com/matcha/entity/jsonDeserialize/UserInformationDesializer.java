package com.matcha.entity.jsonDeserialize;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.matcha.entity.UserInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserInformationDesializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String sex = node.get("sex").asText();
        Integer age = node.get("age").asInt();
        String country = node.get("country").asText();
        String state = node.get("state").asText();
        String aboutMe = node.get("aboutMe").asText();
        String intrestsJson = node.get("interests").asText();
        ArrayList<String> interests  = new ArrayList<>(Arrays.asList(intrestsJson.split("\\#")));
        interests.removeAll(Arrays.asList(""));
        String sexPref = node.get("sexPref").asText();
        Double latitude = (node.get("latitude") == null) ? 0 :node.get("latitude").asDouble();
        Double langitude = (node.get("langitude") == null) ? 0 : node.get("langitude").asDouble();
        Integer likeNumber = (node.get("likeCount") == null) ? 0 : node.get("likeCount").asInt();
        UserInformation info = new UserInformation(sex, age, country, state, aboutMe, interests, sexPref, langitude, latitude, likeNumber);
        return info;
    }
}
