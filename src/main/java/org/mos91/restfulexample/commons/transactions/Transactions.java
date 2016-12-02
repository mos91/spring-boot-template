package org.mos91.restfulexample.commons.transactions;

import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public final class Transactions {

  private Transactions() {

  }

  public static TransactionTemplateCallBuilder executedBy(TransactionTemplate transactionTemplate) {
    return new TransactionTemplateCallBuilder(transactionTemplate);
  }

  public static class TransactionTemplateCallBuilder {

    private TransactionTemplate transactionTemplate;

    public TransactionTemplateCallBuilder(TransactionTemplate transactionTemplate) {
      this.transactionTemplate = transactionTemplate;
    }

    public <R> TransactionTemplateCall<R> body(TransactionCallback<R> callback) {
      return new TransactionTemplateCall(transactionTemplate, callback);
    }

  }
}
