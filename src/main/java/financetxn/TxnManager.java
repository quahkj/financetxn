package financetxn;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TxnManager {

    private List<TxnRecord> txnRecordList;
    private DateTimeFormatter dateTimeFormatter;
    private Set<String> validTransactionIdSet;

    public TxnManager(List<TxnRecord> txnRecordList, String accountId, String from, String to) {
        this.txnRecordList = txnRecordList;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.preprocess(accountId, from, to);
    }

    /*
     * find the matched transaction id and filter the reversal transaction id
     */
    private void preprocess(String accountId, String from, String to) {
        LocalDateTime fromDateTime = LocalDateTime.parse(from, this.dateTimeFormatter);
        LocalDateTime toDateTime = LocalDateTime.parse(to, this.dateTimeFormatter);

        Set<String> paymentTransactionIdSet = this.txnRecordList.stream()
                .filter(txnRecord ->
                    (txnRecord.getFromAccountId().equalsIgnoreCase(accountId)
                        &&
                            (
                                txnRecord.getCreatedAt().isAfter(fromDateTime)
                                &&
                                txnRecord.getCreatedAt().isBefore(toDateTime)
                            )
                    )
                )
                .map(txnRecord -> txnRecord.getTransactionId())
                .collect(Collectors.toSet());

        Set<String> reversalTransactionIdSet = this.txnRecordList.stream()
                .filter(txnRecord ->
                        txnRecord.getTransactionType().equalsIgnoreCase("REVERSAL")
                )
                .map(txnRecord -> txnRecord.getRelatedTransaction())
                .collect(Collectors.toSet());

        paymentTransactionIdSet.removeAll(reversalTransactionIdSet);
        this.validTransactionIdSet = paymentTransactionIdSet;
    }


    public int getNumberOfTransactions() {
        return this.validTransactionIdSet.size();
    }

    public BigDecimal calcRelativeBalance() {

        BigDecimal relativeBalance = BigDecimal.ZERO;
        for(String transactionId : this.validTransactionIdSet) {
            for(TxnRecord txnRecord : this.txnRecordList) {
                if(txnRecord.getTransactionId().equalsIgnoreCase(transactionId)) {
                    relativeBalance = relativeBalance.add(txnRecord.getAmount());
                }
            }
        }

        return relativeBalance.negate();
    }

}
