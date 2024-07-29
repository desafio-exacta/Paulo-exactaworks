package transaction;


import lombok.Data;

@Data
public class TransactionDTO {
    private Long id;
    private String type;
    private Double amount;
    private String details;
    private String senderAccountNumber;
    private String receiverAccountNumber;
}

