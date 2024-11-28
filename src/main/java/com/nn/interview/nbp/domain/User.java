package com.nn.interview.nbp.domain;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Builder(toBuilder = true)
@Data
public class User {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private List<Account> accounts;
}
