package org.mos91.restfulexample.commons.transactions;

import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

public class TransactionTemplateCall<R> implements Transaction<R> {

  private String name;

  private TransactionTemplate transactionTemplate;

  private TransactionCallback<R> callback;

  public TransactionTemplateCall(String name, TransactionTemplate transactionTemplate, TransactionCallback<R> callback) {
    this.name = name;
    this.transactionTemplate = transactionTemplate;
    this.callback = callback;
  }

  public TransactionTemplateCall(TransactionTemplate transactionTemplate, TransactionCallback<R> callback) {
    this(UUID.randomUUID().toString(), transactionTemplate, callback);
  }

  @Override
  public R execute() {
    return transactionTemplate.execute(callback);
  }
}
