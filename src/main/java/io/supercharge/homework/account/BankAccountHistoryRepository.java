package io.supercharge.homework.account;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.supercharge.homework.transaction.TransactionType;

@Repository
public interface BankAccountHistoryRepository extends JpaRepository<BankAccountHistory, Long>{

//	@Query("SELECT h FROM BankAccountHistory h WHERE h.account.id = :bankAccountId ORDER BY h.transaction.date")
	List<BankAccountHistory> findByAccountIdOrderByTransactionDate(long bankAccountId);

//	@Query("SELECT h FROM BankAccountHistory h WHERE h.account.id = :bankAccountId AND h.transaction.date = :date")
	List<BankAccountHistory> findByAccountIdAndTransactionDate(long bankAccountId, LocalDate date);

//	@Query("SELECT h FROM BankAccountHistory h WHERE h.account.id = :bankAccountId AND h.transaction.type = :type")
	List<BankAccountHistory> findByAccountIdAndTransactionType(long bankAccountId, TransactionType type);

}
