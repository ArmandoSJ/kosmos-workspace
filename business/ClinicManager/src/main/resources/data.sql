INSERT INTO doctors(doctor_id, name, last_name_paternal, last_name_maternal, specialty) VALUES
(nextval('doctors_sequence'), 'Armando', 'Salazar', 'Jauregui', 'Medicina Interna'),
(nextval('doctors_sequence'), 'Miriam', 'Garcia', 'Flores', 'Cardiología'),
(nextval('doctors_sequence'), 'Oscar', 'Valle', 'Flores', 'Endocrinología'),
(nextval('doctors_sequence'), 'Carolina', 'Beltran', 'Lopez', 'Psiquiatría'),
(nextval('doctors_sequence'), 'Nelson', 'Pineda', 'Peña', 'Pediatría');

INSERT INTO clinics(clinic_id, room_number, floor) VALUES
(nextval('clinics_sequence'), '14', 2),
(nextval('clinics_sequence'), '52', 6),
(nextval('clinics_sequence'), '25', 3),
(nextval('clinics_sequence'), '42', 5),
(nextval('clinics_sequence'), '30', 4);
