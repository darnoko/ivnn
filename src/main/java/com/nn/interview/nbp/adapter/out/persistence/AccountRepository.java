package com.nn.interview.nbp.adapter.out.persistence;

import com.nn.interview.nbp.adapter.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
