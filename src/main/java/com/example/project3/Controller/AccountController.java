package com.example.project3.Controller;


import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor


public class AccountController {


    private final AccountService accountService;

    //for ADMIN
    @GetMapping("/all")
    public ResponseEntity getAllAccounts() {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }


    @GetMapping("/{id}")
    public ResponseEntity getAccountById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(accountService.getAccountById(id));
    }

    //Admin adds account to customer
    @PostMapping("/admin/add/{customerId}")
    public ResponseEntity addAccount(@PathVariable Integer customerId, @RequestBody @Valid Account account) {
        accountService.addAccount(customerId, account);
        return ResponseEntity.ok().body("account added successfully");
    }

    //
    @PutMapping("/update/{id}")
    public ResponseEntity updateAccount(@PathVariable Integer id, @RequestBody @Valid Account account) {
        accountService.updateAccount(id, account);
        return ResponseEntity.ok().body("Update account successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().body("Delete account successfully");
    }

    @PostMapping("/create")
    public ResponseEntity createAccount(@AuthenticationPrincipal User user, @RequestBody @Valid Account account) {
        accountService.createAccount(user.getId(), account);
        return ResponseEntity.ok().body("Account created successfully");
    }

    @PutMapping("/activate/{accountId}")
    public ResponseEntity activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.activateAccount(user.getId(), accountId);
    return ResponseEntity.ok().body("Account activated successfully");
    }

    @GetMapping("/details/{accountId}")
    public Account getAccountDetails(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        return accountService.getAccountDetails(user.getId(), accountId);
    }

    @GetMapping("/my-accounts")
    public ResponseEntity getUserAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(accountService.getAccountsByUserId(user.getId()));
    }

    @PutMapping("/deposit/{accountId}")
    public ResponseEntity deposit(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestParam double amount) {
        accountService.deposit(user.getId(), accountId, amount);
        return ResponseEntity.ok().body("deposit successfully");
    }

    @PutMapping("/withdraw/{accountId}")
    public ResponseEntity withdraw(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestParam double amount) {
        accountService.withdraw(user.getId(), accountId, amount);
        return ResponseEntity.ok().body("withdraw successfully");
    }

    @PutMapping("/transfer/{fromId}/{toId}")
    public ResponseEntity transfer(@AuthenticationPrincipal User user, @PathVariable Integer fromId, @PathVariable Integer toId, @RequestParam double amount) {
        accountService.transfer(user.getId(), fromId, toId, amount);
        return ResponseEntity.ok().body("transfer successfully");
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.blockAccount(user.getId(), accountId);
        return ResponseEntity.ok().body("block successfully");
    }

}
