package org.example.atm;

import com.fasterxml.jackson.core.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

final class AtmSerializer {
    private final JsonFactory jFactory = new JsonFactory();

    public byte[] serialize(List<Task> tasks) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        JsonGenerator generator = jFactory
                .createGenerator(stream, JsonEncoding.UTF8);

        generator.writeStartArray();

        for(Task task : tasks) {
            generator.writeStartObject();
            generator.writeNumberField("region", task.region);
            generator.writeNumberField("atmId", task.atmId);
            generator.writeEndObject();
        }

        generator.writeEndArray();
        generator.flush();

        generator.close();
        stream.close();

        return stream.toByteArray();
    }

    public List<Task> deserialize(InputStream inputStream) throws IOException {
        JsonParser jParser = jFactory.createParser(inputStream);

        List<Task> taskList = new ArrayList<>();

        while (jParser.nextToken() != JsonToken.END_ARRAY) {
            int region = -1;
            int priority = -1;
            int id = -1;
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = jParser.currentName();
                if ("region".equals(fieldName)) {
                    jParser.nextToken();
                    region = jParser.getIntValue();
                }
                if ("requestType".equals(fieldName)) {
                    jParser.nextToken();
                    priority = RequestPriority.of(jParser.getText());
                }
                if ("atmId".equals(fieldName)) {
                    jParser.nextToken();
                    id = jParser.getIntValue();
                }
            }
            taskList.add(new Task(id, region, priority));
        }
        jParser.close();
        return taskList;
    }
}
