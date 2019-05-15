package financetxn;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TxnManager {

    private List<TxnRecord> txnRecordList;
    private Set<String> validTransactionIdSet;

    public TxnManager(List<TxnRecord> txnRecordList, String accountId, String from, String to) {
        this.txnRecordList = txnRecordList;
        this.preprocess(accountId, from, to);
    }

    /*
     * find the matched transaction id and filter the reversal transaction id
     */
    private void preprocess(String accountId, String from, String to) {
        LocalDateTime fromDateTime = DateTimeUtil.parse(from);
        LocalDateTime toDateTime = DateTimeUtil.parse(to);

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
            BigDecimal amount = searchTransactionAmount(transactionId);
            relativeBalance = relativeBalance.add(amount);
        }

        return relativeBalance.negate();
    }

    private BigDecimal searchTransactionAmount(String transactionId) {

        TxnRecord txnRecord = new TxnRecord(transactionId);
        int index = Collections.binarySearch(this.txnRecordList, txnRecord, Comparator.comparing(TxnRecord::getTransactionId));

        return this.txnRecordList.get(index).getAmount();
    }

}
