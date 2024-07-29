package transaction;

import account.Account;
import account.AccountRepository;
import exceptions.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Account senderAccount = accountRepository.findByAccountNumber(transactionDTO.getSenderAccountNumber());
        Account receiverAccount = accountRepository.findByAccountNumber(transactionDTO.getReceiverAccountNumber());


        // Verifica se o saldo é suficiente para a transação
        if (senderAccount.getBalance() < transactionDTO.getAmount()) {
            throw new InsufficientFundsException("Saldo insuficiente para a transferência");
        }

        senderAccount.setBalance(senderAccount.getBalance() - transactionDTO.getAmount());
        receiverAccount.setBalance(receiverAccount.getBalance() + transactionDTO.getAmount());

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        Transaction transaction = new Transaction();
        transaction.setType(transactionDTO.getType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDate(LocalDateTime.now());
        transaction.setDetails(transactionDTO.getDetails());
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);

        transaction = transactionRepository.save(transaction);

        return mapToDTO(transaction);
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Long id) {
        return transactionRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setDetails(transaction.getDetails());
        dto.setSenderAccountNumber(transaction.getSenderAccount().getAccountNumber());
        dto.setReceiverAccountNumber(transaction.getReceiverAccount().getAccountNumber());
        return dto;
    }
}
