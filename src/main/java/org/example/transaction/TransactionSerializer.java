package org.example.transaction;

import com.fasterxml.jackson.core.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

final class TransactionSerializer {

    private final JsonFactory jFactory = new JsonFactory();

    public byte[] serialize(List<Account> accounts) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonGenerator generator = jFactory
                .createGenerator(stream, JsonEncoding.UTF8);

        generator.writeStartArray();

        for (Account account : accounts) {
            generator.writeStartObject();
            generator.writeStringField("account", account.account);
            generator.writeNumberField("debitCount", account.debitCount);
            generator.writeNumberField("creditCount", account.creditCount);
            generator.writeNumberField("balance", account.balance);
            generator.writeEndObject();
        }

        generator.writeEndArray();
        generator.flush();

        generator.close();
        stream.close();

        return stream.toByteArray();
    }

    public List<Transaction> deserialize(InputStream inputStream) throws IOException {
        JsonParser jParser = jFactory.createParser(inputStream);

        List<Transaction> transactions = new ArrayList<>();

        while (jParser.nextToken() != JsonToken.END_ARRAY) {
            String creditAccount = null;
            String debitAccount = null;
            BigDecimal amount = null;

            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = jParser.currentName();
                if ("debitAccount".equals(fieldName)) {
                    jParser.nextToken();
                    debitAccount = jParser.getText();
                }
                if ("creditAccount".equals(fieldName)) {
                    jParser.nextToken();
                    creditAccount = jParser.getText();
                }
                if ("amount".equals(fieldName)) {
                    jParser.nextToken();
                    amount = jParser.getDecimalValue();
                }
            }
            transactions.add(new Transaction(debitAccount, creditAccount, amount));
        }
        jParser.close();
        return transactions;
    }
}