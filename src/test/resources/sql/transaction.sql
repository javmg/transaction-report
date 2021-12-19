INSERT INTO `customer` (id, default_currency) VALUES
('javier.moreno'   , 'CHF'),
('john.smith'      , 'CHF'),
('customer.no.accounts', 'CHF');

INSERT INTO `account` (id, iban, bic, bank_name, bank_address) VALUES
('3afa799e-7812-4cfe-ae0a-a3a4d048f567', 'CH7512345678901234567'      , 'POFICHBEXXX', 'PostFinance'    , 'Bern'),
('f888ed21-ecc4-488c-aa6f-b01bb374c70e', 'CH8512345678901234567'      , 'POFICHBEXXX', 'PostFinance'    , 'Bern'),
('2ad20acc-dd39-4615-a383-48f62cfddc89', 'FR7630006000011234567890189', 'AGRIFRPP'   , 'Credit Agricole', 'Paris');

INSERT INTO `customer_account` (customer_id, account_id) VALUES
('javier.moreno', '3afa799e-7812-4cfe-ae0a-a3a4d048f567'),
('javier.moreno', 'f888ed21-ecc4-488c-aa6f-b01bb374c70e'),
('john.smith'   , '2ad20acc-dd39-4615-a383-48f62cfddc89');

INSERT INTO `transaction` (id, currency, amount, iban, business_date, description) VALUES
('de8b7586-bc1e-40bb-bc19-dd8c6049f561', 'USD', 10, 'CH7512345678901234567'      , '2021-01-01', 'Amazon subscription'),
('76ec6eef-0c57-49e4-bdb0-fc4bc217158f', 'GBP', 10, 'CH8512345678901234567'      , '2021-01-01', 'Google subscription'),
('a937b18d-86fd-4330-ac1c-eaeacf7a9505', 'EUR', 80, 'FR7630006000011234567890189', '2021-01-02', 'Invoice'),
('250c8161-8381-44c3-87a4-d643bd927177', 'CHF', 50, 'CH7512345678901234567'      , '2021-01-05', 'Software payment');
