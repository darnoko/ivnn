package com.nn.interview.nbp.adapter.in.web;

import com.nn.interview.nbp.adapter.in.dto.AccountsResponseDto;
import com.nn.interview.nbp.adapter.in.dto.UserRequestDto;
import com.nn.interview.nbp.adapter.in.dto.UserResponseDto;
import com.nn.interview.nbp.application.service.AccountService;
import com.nn.interview.nbp.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity createUser(@Validated @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto createdUser = userService.createUser(userRequestDto);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userName}")
                .buildAndExpand(createdUser.getUserName())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{userName}/accounts")
    public ResponseEntity<AccountsResponseDto> getUserAccounts(@PathVariable String userName) {
        return ResponseEntity.ok(accountService.getUserAccounts(userName));
    }
}

