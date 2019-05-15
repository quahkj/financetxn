package financetxn;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

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


//    @Test
//    public void getRecords_shouldReturnNull_whenInitialiseWithNonExistFile() {
//
//        List<TxnRecord> actualTxnRecordList = null;
//        try {
//            String currentPath = new File(".").getCanonicalPath();
//            CSVReader csvReader = new CSVReader(currentPath + "\\testfilefdsfgd.csv");
//            actualTxnRecordList = csvReader.getRecords();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(actualTxnRecordList, null);
//    }
}
