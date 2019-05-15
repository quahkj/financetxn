package financetxn;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TxnManagerTest {

    private String accountId = "ACC334455";
    private String from = "20/10/2018 12:00:00";
    private String to = "20/10/2018 19:00:00";

    @Test
    public void calcRelativeBalance_shouldCalcRelativeAccountBalanceBasedOnProvidedInputs() {


        TxnManager txnManager = new TxnManager(getMockedTxnRecordList(), accountId, from, to);
        BigDecimal relativeBalance = txnManager.calcRelativeBalance();
        BigDecimal expectedRelativeBalance = BigDecimal.valueOf(-25.00);
        assertEquals(relativeBalance.compareTo(expectedRelativeBalance), 0);
    }

    @Test
    public void getNumberOfTransactions_shouldCountNumberOfTransactionsBasedOnProvidedInputs() {

        TxnManager txnManager = new TxnManager(getMockedTxnRecordList(), accountId, from, to);
        int numberOfTransactions = txnManager.getNumberOfTransactions();
        assertEquals(numberOfTransactions, 1);
    }

    private List<TxnRecord> getMockedTxnRecordList() {

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
}
