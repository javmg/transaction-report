package com.jmgits.transactionreport.trx.repository;

import static java.util.Optional.ofNullable;

import com.jmgits.transactionreport.base.exception.NotFoundException;
import com.jmgits.transactionreport.trx.domain.QTransactionEntity;
import com.jmgits.transactionreport.trx.domain.TransactionEntity;
import com.jmgits.transactionreport.trx.view.TransactionSearch;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String>, QuerydslPredicateExecutor<TransactionEntity> {

    default TransactionEntity getById(String id) {

        return findById(id).orElseThrow(() -> new NotFoundException(
            "TRANSACTION_NOT_FOUND",
            String.format("Transaction with id '%s' not found.", id)
        ));
    }

    default Page<TransactionEntity> search(TransactionSearch criteria, Pageable page) {

        var qTransactionEntity = QTransactionEntity.transactionEntity;

        var clause = new BooleanBuilder();

        ofNullable(criteria.getAccountIbans()).ifPresent(ibans ->
                clause.and(qTransactionEntity.iban.in(ibans))
        );

        ofNullable(criteria.getBusinessDateFrom()).ifPresent(date ->
                clause.and(qTransactionEntity.businessDate.goe(date))
        );

        ofNullable(criteria.getBusinessDateTo()).ifPresent(date ->
                clause.and(qTransactionEntity.businessDate.loe(date))
        );

        return this.findAll(clause, page);
    }

}
