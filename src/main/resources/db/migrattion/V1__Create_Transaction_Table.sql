CREATE TABLE account (
                         id SERIAL PRIMARY KEY,
                         account_number VARCHAR(255) NOT NULL,
                         balance DOUBLE PRECISION NOT NULL
);

CREATE TABLE transaction (
                             id SERIAL PRIMARY KEY,
                             type VARCHAR(255) NOT NULL,
                             amount DOUBLE PRECISION NOT NULL,
                             date TIMESTAMP NOT NULL,
                             details TEXT,
                             sender_account_id INTEGER,
                             receiver_account_id INTEGER,
                             CONSTRAINT fk_sender_account
                                 FOREIGN KEY(sender_account_id)
                                     REFERENCES account(id),
                             CONSTRAINT fk_receiver_account
                                 FOREIGN KEY(receiver_account_id)
                                     REFERENCES account(id)
);
