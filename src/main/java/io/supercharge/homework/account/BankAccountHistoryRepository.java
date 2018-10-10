package io.supercharge.homework.account;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.supercharge.homework.transaction.TransactionType;

@Repository
public interface BankAccountHistoryRepository extends JpaRepository<BankAccountHistory, Long>{

	List<BankAccountHistory> findByAccountIdOrderByTransactionDate(long bankAccountId);

	List<BankAccountHistory> findByAccountIdAndTransactionDate(long bankAccountId, LocalDate date);

	List<BankAccountHistory> findByAccountIdAndTransactionType(long bankAccountId, TransactionType type);

}
