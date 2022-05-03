
INSERT INTO account_no (next_val) SELECT "2002022001" FROM DUAL WHERE NOT EXISTS (SELECT next_val FROM account_no);

INSERT INTO transaction_no (next_val) SELECT "3001" FROM DUAL WHERE NOT EXISTS (SELECT next_val FROM transaction_no);