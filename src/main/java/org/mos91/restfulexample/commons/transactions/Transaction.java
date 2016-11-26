package org.mos91.restfulexample.commons.transactions;

@FunctionalInterface
public interface Transaction<R> {

  R execute();

}
