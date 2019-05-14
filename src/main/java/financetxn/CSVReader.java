package financetxn;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    private String fileName;
    private DateTimeFormatter dateTimeFormatter;

    public CSVReader(String fileName) {
        this.fileName = fileName;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }

    public List<TxnRecord> getRecords() throws IOException {

        Reader reader = new FileReader(new File(this.fileName));
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
        List<TxnRecord> txnRecordList = new ArrayList<>();

        for(CSVRecord record: records) {
            TxnRecord txnRecord = new TxnRecord();
            txnRecord.setTransactionId(record.get("transactionId"));
            txnRecord.setFromAccountId(record.get("fromAccountId"));
            txnRecord.setToAccountId(record.get("toAccountId"));
            txnRecord.setCreatedAt(LocalDateTime.parse(record.get("createdAt"), this.dateTimeFormatter));
            txnRecord.setAmount(BigDecimal.valueOf(Double.parseDouble(record.get("amount"))));
            txnRecord.setTransactionType(record.get("transactionType"));
            txnRecord.setRelatedTransaction(record.get("relatedTransaction"));
            txnRecordList.add(txnRecord);
        }

        return txnRecordList;
    }
}
