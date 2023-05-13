package org.example.game;

import com.fasterxml.jackson.core.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameSerializer {

    private final JsonFactory jFactory = new JsonFactory();

    public byte[] serialize(List<List<Clan>> groups) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        JsonGenerator generator = jFactory
                .createGenerator(stream, JsonEncoding.UTF8);

        generator.writeStartArray();

        for(List<Clan> group : groups) {
            generator.writeStartArray();

            for(Clan clan : group) {
                generator.writeStartObject();

                generator.writeNumberField("numberOfPlayers", clan.numberOfPlayers);
                generator.writeNumberField("points", clan.points);

                generator.writeEndObject();
            }
            generator.writeEndArray();
        }

        generator.writeEndArray();
        generator.flush();

        generator.close();
        stream.close();

        return stream.toByteArray();
    }

    public Players deserialize(InputStream inputStream) throws IOException {

        List<Clan> clans = new ArrayList<>();
        int groupCount = 0;

        JsonParser jParser = jFactory.createParser(inputStream);

        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jParser.currentName();
            if ("groupCount".equals(fieldName)) {
                jParser.nextToken();
                groupCount = jParser.getIntValue();
            }
            if ("clans".equals(fieldName)) {
                jParser.nextToken();

                while (jParser.nextToken() != JsonToken.END_ARRAY) {
                    int numberOfPlayers = -1;
                    int points = -1;
                    while (jParser.nextToken() != JsonToken.END_OBJECT) {
                        fieldName = jParser.currentName();
                        if ("numberOfPlayers".equals(fieldName)) {
                            jParser.nextToken();
                            numberOfPlayers = jParser.getIntValue();
                        }
                        if ("points".equals(fieldName)) {
                            jParser.nextToken();
                            points = jParser.getIntValue();
                        }
                    }
                    clans.add(new Clan(numberOfPlayers, points));
                }
            }
        }

        jParser.close();

        return new Players(clans, groupCount);
    }
}
