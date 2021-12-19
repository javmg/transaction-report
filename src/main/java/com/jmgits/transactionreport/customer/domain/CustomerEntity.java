package com.jmgits.transactionreport.customer.domain;

import com.jmgits.transactionreport.trx.domain.AccountEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CustomerEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToMany
    @JoinTable(name = "customer_account",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
    private Set<AccountEntity> accounts = new LinkedHashSet<>();

    @Column(name = "default_currency", nullable = false)
    private String defaultCurrency;

}
