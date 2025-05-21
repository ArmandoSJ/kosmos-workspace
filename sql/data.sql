INSERT INTO role (name) VALUES ('CUSTOMER'), ('ADMINISTRATOR');

INSERT INTO module (name, base_path) VALUES ('CUSTOMER', '/customers'),('AUTH', '/auth'),
('PERMISSION', '/permissions'), ('DOCTOR', '/doctors');

INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES
('READ_ALL_CUSTOMERS','', 'GET', false, 1), ('REGISTER_ONE','', 'POST', true, 1),

('AUTHENTICATE','/authenticate', 'POST', true, 2), ('VALIDATE-TOKEN','/validate-token', 'GET', true, 2),
('LOGOUT','/logout','POST', true, 2), ('READ_MY_PROFILE','/profile','GET', false, 2),

('READ_MY_PROFILE','/profile','GET', false, 2), ('READ_ONE_PERMISSION','/[0-9]*','GET', false, 3),
('CREATE_ONE_PERMISSION','','POST', false, 3), ('DELETE_ONE_PERMISSION','/[0-9]*','DELETE', false, 3),

('READ_ALL_DOCTORS', '', 'GET', false, 4);


INSERT INTO granted_permission (role_id, operation_id) VALUES
(1, 6),
(2, 1), (2, 6), (2, 7), (2, 8), (2, 9), (2, 10), (2, 11);


-- dev credentials
-- user: asalazarj
-- password : Temporal@32
INSERT INTO users (username, name, password, role_id) VALUES ('asalazarj', 'Armando Salazar', '$2a$10$Pp1QKuLURYKH/ijzicVR8upDeTNECCFeiXERlODJBqDUVv0DT6Fsi', 2);
