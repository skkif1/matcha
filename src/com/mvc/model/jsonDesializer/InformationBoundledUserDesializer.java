package com.mvc.model.jsonDesializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.mvc.Entity.InformationBoundledUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class InformationBoundledUserDesializer extends JsonDeserializer {


    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();
        String sex = node.get("sex").asText();
        Integer age = node.get("age").asInt();
        String country = node.get("country").asText();
        String state = node.get("state").asText();
        String aboutMe = node.get("aboutMe").asText();
        String intrestsJson = node.get("interests").asText();
        ArrayList<String> interests  = new ArrayList<>(Arrays.asList(intrestsJson.split("\\#")));
        String sexPref = node.get("sexPref").asText();
        InformationBoundledUser user = new InformationBoundledUser(firstName, lastName,  sex, age,
                country, state, aboutMe, interests, sexPref);
        return user;
    }
}
