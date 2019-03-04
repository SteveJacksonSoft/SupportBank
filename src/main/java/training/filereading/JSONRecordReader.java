package training.filereading;

import com.google.gson.*;
import training.bank.TransactionRecord;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JSONRecordReader implements Reader {
    private Gson gson;

    private static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) -> {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(jsonElement.getAsString(), format);
        });

        return gsonBuilder.create();
    }

    public JSONRecordReader() {
        this.gson = createGson();
    }
    
    public List<TransactionRecord> getRecordsFromFile(String filePath) throws IOException, JsonSyntaxException {
        String jsonFromFile;
        try {
            jsonFromFile = this.readFile(filePath);
        } catch (IOException e) {
            throw new IOException("There was a problem reading the file " + filePath);
        }
        return Stream.of(gson.fromJson(jsonFromFile, JSONTransaction[].class))
                .map(JSONTransaction::toTransactionRecord)
                .collect(Collectors.toList());
    }

    private String readFile(String filePath) throws IOException {
        List<String> fileLines = java.nio.file.Files.readAllLines(Paths.get(filePath));
        return String.join("", fileLines);
    }

    private static class JSONTransaction {
        private LocalDate date;
        private String fromAccount;
        private String toAccount;
        private String narrative;
        private BigDecimal amount;

        private JSONTransaction(LocalDate date, String fromAccount, String toAccount, String narrative, BigDecimal amount) {
            this.date = date;
            this.fromAccount = fromAccount;
            this.toAccount = toAccount;
            this.narrative = narrative;
            this.amount = amount;
        }

        private TransactionRecord toTransactionRecord() {
            return new TransactionRecord(date, fromAccount, toAccount, narrative, amount);
        }
    }
}
