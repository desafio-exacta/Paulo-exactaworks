package com.example.extractaworks;


import account.Account;
import account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import transaction.Transaction;
import transaction.TransactionDTO;
import transaction.TransactionRepository;
import transaction.TransactionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction() {
        Account senderAccount = new Account();
        senderAccount.setAccountNumber("123");
        senderAccount.setBalance(200.0);

        Account receiverAccount = new Account();
        receiverAccount.setAccountNumber("456");
        receiverAccount.setBalance(100.0);

        when(accountRepository.findByAccountNumber("123")).thenReturn(senderAccount);
        when(accountRepository.findByAccountNumber("456")).thenReturn(receiverAccount);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setType("DEPOSIT");
        transaction.setAmount(100.0);
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setType("DEPOSIT");
        transactionDTO.setAmount(100.0);
        transactionDTO.setSenderAccountNumber("123");
        transactionDTO.setReceiverAccountNumber("456");

        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);

        assertEquals(transactionDTO.getType(), createdTransaction.getType());
        assertEquals(transactionDTO.getAmount(), createdTransaction.getAmount());
    }

    @Test
    void testGetAllTransactions() {
        Account senderAccount = new Account();
        senderAccount.setAccountNumber("123");

        Account receiverAccount = new Account();
        receiverAccount.setAccountNumber("456");

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));

        List<TransactionDTO> transactions = transactionService.getAllTransactions();

        assertEquals(1, transactions.size());
        assertEquals("123", transactions.get(0).getSenderAccountNumber());
        assertEquals("456", transactions.get(0).getReceiverAccountNumber());
    }

    @Test
    void testGetTransactionById() {
        Account senderAccount = new Account();
        senderAccount.setAccountNumber("123");

        Account receiverAccount = new Account();
        receiverAccount.setAccountNumber("456");

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        TransactionDTO transactionDTO = transactionService.getTransactionById(1L);

        assertEquals(transaction.getId(), transactionDTO.getId());
        assertEquals("123", transactionDTO.getSenderAccountNumber());
        assertEquals("456", transactionDTO.getReceiverAccountNumber());
    }
}
