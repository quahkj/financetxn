package financetxn;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CSVReaderTest {

    @Test
    public void getRecords_shouldReturnCsvRecords_whenInitialiseCsvReaderWithAFile() {

        List<TxnRecord> actualTxnRecordList = null;
        try {
            String currentPath = new File(".").getCanonicalPath();
            CSVReader csvReader = new CSVReader(currentPath + "\\testfile.csv");
            actualTxnRecordList = csvReader.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(actualTxnRecordList.size(), 5);
        assertEquals(actualTxnRecordList.get(0).getTransactionId(), "TX10001");
        assertEquals(actualTxnRecordList.get(0).getFromAccountId(), "ACC334455");
        assertEquals(actualTxnRecordList.get(0).getToAccountId(), "ACC778899");
        assertEquals(actualTxnRecordList.get(0).getCreatedAt().toString(), "2018-10-20T12:47:55");
        assertEquals(actualTxnRecordList.get(0).getAmount(), BigDecimal.valueOf(25.0));
        assertEquals(actualTxnRecordList.get(0).getTransactionType(), "PAYMENT");
        assertEquals(actualTxnRecordList.get(0).getRelatedTransaction(), "");

        assertEquals(actualTxnRecordList.get(1).getTransactionId(), "TX10002");
        assertEquals(actualTxnRecordList.get(1).getFromAccountId(), "ACC334455");
        assertEquals(actualTxnRecordList.get(1).getToAccountId(), "ACC998877");
        assertEquals(actualTxnRecordList.get(1).getCreatedAt().toString(), "2018-10-20T17:33:43");
        assertEquals(actualTxnRecordList.get(1).getAmount(), BigDecimal.valueOf(10.5));
        assertEquals(actualTxnRecordList.get(1).getTransactionType(), "PAYMENT");
        assertEquals(actualTxnRecordList.get(1).getRelatedTransaction(), "");

        assertEquals(actualTxnRecordList.get(2).getTransactionId(), "TX10003");
        assertEquals(actualTxnRecordList.get(2).getFromAccountId(), "ACC998877");
        assertEquals(actualTxnRecordList.get(2).getToAccountId(), "ACC778899");
        assertEquals(actualTxnRecordList.get(2).getCreatedAt().toString(), "2018-10-20T18:00");
        assertEquals(actualTxnRecordList.get(2).getAmount(), BigDecimal.valueOf(5.0));
        assertEquals(actualTxnRecordList.get(2).getTransactionType(), "PAYMENT");
        assertEquals(actualTxnRecordList.get(2).getRelatedTransaction(), "");

        assertEquals(actualTxnRecordList.get(3).getTransactionId(), "TX10004");
        assertEquals(actualTxnRecordList.get(3).getFromAccountId(), "ACC334455");
        assertEquals(actualTxnRecordList.get(3).getToAccountId(), "ACC998877");
        assertEquals(actualTxnRecordList.get(3).getCreatedAt().toString(), "2018-10-20T19:45");
        assertEquals(actualTxnRecordList.get(3).getAmount(), BigDecimal.valueOf(10.5));
        assertEquals(actualTxnRecordList.get(3).getTransactionType(), "REVERSAL");
        assertEquals(actualTxnRecordList.get(3).getRelatedTransaction(), "TX10002");

        assertEquals(actualTxnRecordList.get(4).getTransactionId(), "TX10005");
        assertEquals(actualTxnRecordList.get(4).getFromAccountId(), "ACC334455");
        assertEquals(actualTxnRecordList.get(4).getToAccountId(), "ACC778899");
        assertEquals(actualTxnRecordList.get(4).getCreatedAt().toString(), "2018-10-21T09:30");
        assertEquals(actualTxnRecordList.get(4).getAmount(), BigDecimal.valueOf(7.25));
        assertEquals(actualTxnRecordList.get(4).getTransactionType(), "PAYMENT");
        assertEquals(actualTxnRecordList.get(4).getRelatedTransaction(), "");
    }

    private List<TxnRecord> getExpectedTxnRecordList() {

        List<TxnRecord> txnRecordTestData = new ArrayList<>();
        txnRecordTestData.add(
            createTxnRecord("TX10001",
                            "ACC334455",
                            "ACC778899",
                            "20/10/2018 12:47:55",
                            "25.00",
                            "PAYMENT",
                            "")
        );
        txnRecordTestData.add(
                createTxnRecord("TX10002",
                        "ACC334455",
                        "ACC998877",
                        "20/10/2018 17:33:43",
                        "10.50",
                        "PAYMENT",
                        "")
        );
        txnRecordTestData.add(
                createTxnRecord("TX10003",
                        "ACC998877",
                        "ACC778899",
                        "20/10/2018 18:00:00",
                        "5.00",
                        "PAYMENT",
                        "")
        );
        txnRecordTestData.add(
                createTxnRecord("TX10004",
                        "ACC334455",
                        "ACC998877",
                        "20/10/2018 19:45:00",
                        "10.50",
                        "REVERSAL",
                        "TX10002")
        );
        txnRecordTestData.add(
                createTxnRecord("TX10005",
                        "ACC334455",
                        "ACC778899",
                        "21/10/2018 09:30:00",
                        "7.25",
                        "PAYMENT",
                        "")
        );
        return txnRecordTestData;
    }

    private TxnRecord createTxnRecord(String transactionId,
                                      String fromAccountId,
                                      String toAcccountId,
                                      String createdAt,
                                      String amount,
                                      String transactionType,
                                      String relatedTransaction) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        TxnRecord txnRecord = new TxnRecord();
        txnRecord.setTransactionId(transactionId);
        txnRecord.setFromAccountId(fromAccountId);
        txnRecord.setToAccountId(toAcccountId);
        txnRecord.setCreatedAt(LocalDateTime.parse(createdAt, dateTimeFormatter));
        txnRecord.setAmount(BigDecimal.valueOf(Double.parseDouble(amount)));
        txnRecord.setTransactionType(transactionType);
        txnRecord.setRelatedTransaction(relatedTransaction);
        return txnRecord;
    }

/*    @Test
    public void getRecords_shouldReturnNull_whenInitialiseWithNonExistFile() {


    }*/
}
