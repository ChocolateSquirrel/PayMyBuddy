INSERT INTO utilisateur(id, first_name, last_name, mail, password) VALUES (NEXTVAL('utilisateur_id_seq'),'Sherlock','Holmes','sherlock.holmes@gmail.com','$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm');
INSERT INTO paymybuddy_account(id, number, balance, user_id) VALUES (NEXTVAL('paymybuddy_account_id_seq'),'compte de Sherlock','0.0','1');
INSERT INTO role(id, role) VALUES (NEXTVAL('role_id_seq'),'user');
INSERT INTO utilisateur_roles(user_id, roles_id) VALUES ('1','1');

INSERT INTO utilisateur(id, first_name, last_name, mail, password) VALUES (NEXTVAL('utilisateur_id_seq'),'Peter','Pan','peter.pan@gmail.com','$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm');
INSERT INTO paymybuddy_account(id, number, balance, user_id) VALUES (NEXTVAL('paymybuddy_account_id_seq'),'compte de Peter Pzn','0.0','2');
INSERT INTO role(id, role) VALUES (NEXTVAL('role_id_seq'),'user');
INSERT INTO utilisateur_roles(user_id, roles_id) VALUES ('2','2');

INSERT INTO utilisateur(id, first_name, last_name, mail, password) VALUES (NEXTVAL('utilisateur_id_seq'),'Harry','Potter','harry.potter@gmail.com','$2a$10$a0hlNqCasHViWB8VkJrjj./z1OgM9WShM6B6BJNOwCJuQJ4sSS9gm');
INSERT INTO paymybuddy_account(id, number, balance, user_id) VALUES (NEXTVAL('paymybuddy_account_id_seq'),'compte de Harry','0.0','3');
INSERT INTO role(id, role) VALUES (NEXTVAL('role_id_seq'),'user');
INSERT INTO utilisateur_roles(user_id, roles_id) VALUES ('3','3');
