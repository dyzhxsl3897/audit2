package com.dyzhxsl.audit.bl.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LOAN")
public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;

	private int loanId;

	private User fromUser;

	private User toUser;

	private double money;

	private Date loanTime;

	private String notes;

	private Date auditTime;

	private User auditUser;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FROM_USER")
	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TO_USER")
	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AUDIT_USER")
	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
	}

	@Column(name = "MONEY")
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Id
	@GeneratedValue
	@Column(name = "LOAN_ID")
	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	@Column(name = "LOAN_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	@Column(name = "NOTES")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "AUDIT_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
}
