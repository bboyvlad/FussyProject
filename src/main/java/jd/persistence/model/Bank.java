package jd.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduardom on 11/3/16.
 */

@Entity
@Table(name = "Bank")
public class Bank {

    private Long bankid;
    private String bankname;
    private String bankacctnum;
    private double bankbalance;
    private Date dcreate;
    private String notes;
    private boolean enabled;
    private boolean deleted;
    private Set<TransactionBank> transactions = new HashSet<TransactionBank>(0);

    public Bank() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_ID", unique = true, nullable = false)
    public Long getBankid() {
        return bankid;
    }

    public void setBankid(Long bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankacctnum() {
        return bankacctnum;
    }

    public void setBankacctnum(String bankacctnum) {
        this.bankacctnum = bankacctnum;
    }

    public double getBankbalance() {
        return bankbalance;
    }

    public void setBankbalance(double bankbalance) {
        this.bankbalance = bankbalance;
    }

    public Date getDcreate() {
        return dcreate;
    }

    public void setDcreate(Date dcreate) {
        this.dcreate = dcreate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Bank_Transaction", joinColumns = {
            @JoinColumn(name = "BANK_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "TRANSBANK_ID",
                    nullable = false, updatable = false) })
    public Set<TransactionBank> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionBank> transactions) {
        this.transactions = transactions;
    }
}
