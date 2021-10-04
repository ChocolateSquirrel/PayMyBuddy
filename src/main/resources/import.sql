INSERT INTO utilisateur(id, first_name, last_name, mail, password) VALUES (NEXTVAL('utilisateur_id_seq'),'Sherlock','Holmes','sherlock.holmes@gmail.com','$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm');
INSERT INTO paymybuddy_account(id, number, balance, user_id) VALUES (NEXTVAL('paymybuddy_account_id_seq'),'compte PMB de Sherlock','74.87','1');
INSERT INTO role(id, role) VALUES (NEXTVAL('role_id_seq'),'user');
INSERT INTO utilisateur_roles(user_id, roles_id) VALUES ('1','1');

INSERT INTO utilisateur(id, first_name, last_name, mail, password) VALUES (NEXTVAL('utilisateur_id_seq'),'Peter','Pan','peter.pan@gmail.com','$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm');
INSERT INTO paymybuddy_account(id, number, balance, user_id) VALUES (NEXTVAL('paymybuddy_account_id_seq'),'compte PMB de Peter Pan','0.0','2');
INSERT INTO role(id, role) VALUES (NEXTVAL('role_id_seq'),'user');
INSERT INTO utilisateur_roles(user_id, roles_id) VALUES ('2','2');

INSERT INTO utilisateur(id, first_name, last_name, mail, password) VALUES (NEXTVAL('utilisateur_id_seq'),'Harry','Potter','harry.potter@gmail.com','$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm');
INSERT INTO paymybuddy_account(id, number, balance, user_id) VALUES (NEXTVAL('paymybuddy_account_id_seq'),'compte PMB de Harry','75','3');
INSERT INTO role(id, role) VALUES (NEXTVAL('role_id_seq'),'user');
INSERT INTO utilisateur_roles(user_id, roles_id) VALUES ('3','3');

INSERT INTO utilisateur(id, first_name, last_name, mail, password) VALUES (NEXTVAL('utilisateur_id_seq'),'Bugs','Bunny','bugs.bunny@gmail.com','$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm');
INSERT INTO paymybuddy_account(id, number, balance, user_id) VALUES (NEXTVAL('paymybuddy_account_id_seq'),'compte PMB de Bugs Bunny','0.0','4');
INSERT INTO role(id, role) VALUES (NEXTVAL('role_id_seq'),'user');
INSERT INTO utilisateur_roles(user_id, roles_id) VALUES ('4','4');

INSERT INTO utilisateur_contacts(user_id, contacts_id) VALUES ('1', '2');
INSERT INTO utilisateur_contacts(user_id, contacts_id) VALUES ('1', '3');
INSERT INTO utilisateur_contacts(user_id, contacts_id) VALUES ('2', '1');
INSERT INTO utilisateur_contacts(user_id, contacts_id) VALUES ('3', '1');
INSERT INTO utilisateur_contacts(user_id, contacts_id) VALUES ('4', '1');

INSERT INTO bank_account(id, balance, iban, user_id) VALUES (NEXTVAL('bank_account_id_seq'), '1000', 'FR76Sherlock', '1');
INSERT INTO bank_account(id, balance, iban, user_id) VALUES (NEXTVAL('bank_account_id_seq'), '500', 'FR77Sherlock', '1');
INSERT INTO bank_account(id, balance, iban, user_id) VALUES (NEXTVAL('bank_account_id_seq'), '1000', 'FR76Peter', '2');
INSERT INTO bank_account(id, balance, iban, user_id) VALUES (NEXTVAL('bank_account_id_seq'), '1000', 'FR76Harry', '3');

INSERT INTO internal_transaction(id, amount, local_date, signe, bank_account, pmb_account) VALUES (NEXTVAL('internal_transaction_id_seq'), '100', '10/08/2021', '0', '1', '1');
INSERT INTO internal_transaction(id, amount, local_date, signe, bank_account, pmb_account) VALUES (NEXTVAL('internal_transaction_id_seq'), '50', '10/08/2021', '0', '3', '3');

INSERT INTO ext_transaction(id, amount, commission, local_date, description, credit_account, debit_account) VALUES (NEXTVAL('ext_transaction_id_seq'), '25', '0.13', '10/08/2021', 'Anniv', '3', '1');