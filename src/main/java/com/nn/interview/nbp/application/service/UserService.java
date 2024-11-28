package com.nn.interview.nbp.application.service;

import com.nn.interview.nbp.adapter.in.dto.UserRequestDto;
import com.nn.interview.nbp.adapter.in.dto.UserResponseDto;
import com.nn.interview.nbp.adapter.in.exception.UserException;
import com.nn.interview.nbp.adapter.out.persistence.UserRepository;
import com.nn.interview.nbp.adapter.out.persistence.entity.UserEntity;
import com.nn.interview.nbp.application.service.mapper.UserMapper;
import com.nn.interview.nbp.domain.Account;
import com.nn.interview.nbp.domain.Currency;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.nn.interview.nbp.application.service.AccountService.ROUNDING_MODE;
import static com.nn.interview.nbp.application.service.AccountService.SCALE_4;

@RequiredArgsConstructor
@Service
public class UserService {

    public static final int USERNAME_LENGTH = 20;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return Optional.of(userRequestDto)
                .map(userMapper::toUser)
                .map(u -> u.toBuilder().userName(generateUniqueUserName())
                        .accounts(getAccounts(userRequestDto))
                        .build())
                .map(userMapper::toUserEntity)
                .map(userRepository::save)
                .map(userMapper::toUserResponseDto)
                .orElseThrow(() -> new UserException("User can not be created."));
    }

    private List<Account> getAccounts(UserRequestDto userRequestDto) {
        return List.of(
                Account.builder()
                        .currency(Currency.PLN)
                        .balance(userRequestDto.getInitialBalance().setScale(SCALE_4, ROUNDING_MODE))
                        .build(),
                Account.builder()
                        .currency(Currency.USD)
                        .balance(BigDecimal.ZERO.setScale(SCALE_4, ROUNDING_MODE))
                        .build()
        );
    }

    private String generateUniqueUserName() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .build();

        return Stream.generate(() -> generator.generate(USERNAME_LENGTH))
                .filter(userName -> userRepository.findByUserName(userName).isEmpty())
                .findFirst()
                .orElseThrow(() -> new UserException("Unable to create unique userName"));
    }

    public Optional<UserEntity> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
