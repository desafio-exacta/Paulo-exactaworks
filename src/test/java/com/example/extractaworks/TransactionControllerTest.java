package com.example.extractaworks;


import account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import transaction.TransactionController;
import transaction.TransactionDTO;
import transaction.TransactionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setType("DEPOSIT");
        transactionDTO.setAmount(100.0);
        transactionDTO.setSenderAccountNumber("123");
        transactionDTO.setReceiverAccountNumber("456");

        when(transactionService.createTransaction(any(TransactionDTO.class))).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> response = transactionController.createTransaction(transactionDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transactionDTO, response.getBody());
    }

    @Test
    void testGetAllTransactions() {
        TransactionDTO transactionDTO = new TransactionDTO();
        when(transactionService.getAllTransactions()).thenReturn(Collections.singletonList(transactionDTO));

        ResponseEntity<List<TransactionDTO>> response = transactionController.getAllTransactions();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(transactionDTO, response.getBody().get(0));
    }

    @Test
    void testGetTransactionById() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);

        when(transactionService.getTransactionById(1L)).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> response = transactionController.getTransactionById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transactionDTO, response.getBody());
    }
}

