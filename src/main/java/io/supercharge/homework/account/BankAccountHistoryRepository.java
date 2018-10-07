package io.supercharge.homework.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountHistoryRepository extends JpaRepository<BankAccountHistory, Long>{

}
