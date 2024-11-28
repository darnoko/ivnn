package com.nn.interview.nbp.application.service.mapper;

import com.nn.interview.nbp.adapter.in.dto.AccountDto;
import com.nn.interview.nbp.adapter.out.persistence.entity.AccountEntity;
import com.nn.interview.nbp.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    AccountEntity toAccountEntity(Account account);

    Account toAccount(AccountEntity accountEntity);

    AccountDto toAccountDto(Account account);
}
