use prueba_db;

insert into role (role_name) values ("USER");
insert into role (role_name) values ("ADMIN");  

insert into permission (permission_name) values ("CREATE");
insert into permission (permission_name) values ("READ");
insert into permission (permission_name) values ("DELETE");
insert into permission (permission_name) values ("UPDATE");

insert into role_permissions (role_id, permission_id) values (1, 2);
insert into role_permissions (role_id, permission_id) values (2, 1);
insert into role_permissions (role_id, permission_id) values (2, 2);
insert into role_permissions (role_id, permission_id) values (2, 3);
insert into role_permissions (role_id, permission_id) values (2, 4);

insert into area (area_name) values ("Recursos Humanos");
insert into area (area_name) values ("Finanzas");
insert into area (area_name) values ("Tecnologias de la informacion");
insert into area (area_name) values ("Marketing");
insert into area (area_name) values ("Operaciones");

INSERT INTO user (email, password, profile_picture, is_enabled, account_no_expired, account_no_locked, credential_no_expired)
VALUES ('example@example.com', '$2a$10$/yfvz3aJA5aOLHZmf5zYmePAbX6HUxUus00KYL3lY4L8DmX7di9c.', '', TRUE, TRUE, TRUE, TRUE);

INSERT INTO employer (area_id, employer_name, employer_email, employer_phone_number) VALUES
(1, 'Juan Pérez', 'juan.perez@example.com', '123456789'),
(2, 'Ana Gómez', 'ana.gomez@example.com', '987654321'),
(3, 'Carlos Ruiz', 'carlos.ruiz@example.com', '456789123'),
(4, 'María López', 'maria.lopez@example.com', '321654987'),
(5, 'José Martínez', 'jose.martinez@example.com', '654987321'),
(1, 'Lucía Fernández', 'lucia.fernandez@example.com', '789456123'),
(2, 'Miguel Sánchez', 'miguel.sanchez@example.com', '852741963'),
(3, 'Elena Rodríguez', 'elena.rodriguez@example.com', '159753456'),
(4, 'Pedro García', 'pedro.garcia@example.com', '357951468'),
(5, 'Isabel Torres', 'isabel.torres@example.com', '654123789'),
(1, 'Francisco Díaz', 'francisco.diaz@example.com', '963852741'),
(2, 'Paula Castro', 'paula.castro@example.com', '741258963'),
(3, 'Roberto Herrera', 'roberto.herrera@example.com', '123123123'),
(4, 'Laura Ruiz', 'laura.ruiz@example.com', '963456789'),
(5, 'Juanita Gómez', 'juanita.gomez@example.com', '258147369'),
(1, 'Antonio Pérez', 'antonio.perez@example.com', '159632478'),
(2, 'Patricia Martínez', 'patricia.martinez@example.com', '741369852'),
(3, 'Fernando López', 'fernando.lopez@example.com', '753951456'),
(4, 'Sandra Torres', 'sandra.torres@example.com', '369258147'),
(5, 'Raúl Díaz', 'raul.diaz@example.com', '852963741');