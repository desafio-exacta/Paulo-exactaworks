package account;


import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private Double balance;
}
