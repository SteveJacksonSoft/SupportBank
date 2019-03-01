package training.filereading;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import training.bank.TransactionRecord;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

public class JSONRecordReader implements Reader {
    private Gson gson;

    private static Gson createGson() {
        GsonBuilder recordGsonBuilder = new GsonBuilder();

        recordGsonBuilder.registerTypeAdapter(TransactionRecord.class, (JsonDeserializer<TransactionRecord>) (jsonElement, type, jsonDeserializationContext) -> {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new TransactionRecord(LocalDate.parse(jsonObject.get("date").getAsString(), format),
                    jsonObject.get("fromAccount").getAsString(),
                    jsonObject.get("toAccount").getAsString(),
                    jsonObject.get("narrative").getAsString(),
                    new BigDecimal(jsonObject.get("amount").getAsString())
            );
        });

        Gson recordGson = recordGsonBuilder.create();
        Type listType = new TypeToken<HashSet<TransactionRecord>>() {}.getType();
        return new GsonBuilder().registerTypeAdapter(listType, (JsonDeserializer<HashSet<TransactionRecord>>) (element, type, context) -> {
                    HashSet<TransactionRecord> transactionRecords = new HashSet<>();
                    if (element.isJsonArray()) {
                        JsonArray jsonArray = element.getAsJsonArray();
                        jsonArray.forEach(jsonElement -> transactionRecords.add(recordGson.fromJson(jsonElement, TransactionRecord.class)));
                    } else {
                        transactionRecords.add(recordGson.fromJson(element, TransactionRecord.class));
                    }
                    return transactionRecords;
                })
                .create();
    }

    public JSONRecordReader() {
        this.gson = createGson();
    }
    
    public HashSet<TransactionRecord> getRecordsFromFile(String filePath) throws IOException, JsonSyntaxException {
        String jsonFromFile;
        try {
            jsonFromFile = this.readFile(filePath);
        } catch (IOException e) {
            throw new IOException("There was a problem reading the file " + filePath);
        }
        return gson.fromJson(jsonFromFile, new TypeToken<HashSet<TransactionRecord>>() {}.getType());
    }

    private String readFile(String filePath) throws IOException {
        List<String> fileLines = java.nio.file.Files.readAllLines(Paths.get(filePath));
        return String.join("", fileLines);
    }
}
