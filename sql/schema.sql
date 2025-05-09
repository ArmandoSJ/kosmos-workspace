CREATE SEQUENCE IF NOT EXISTS doctors_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS clinics_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS appointments_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE doctors (
    doctor_id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('doctors_sequence'),
    name VARCHAR(200),
    last_name_paternal VARCHAR(120),
    last_name_maternal VARCHAR(120),
    specialty VARCHAR(200),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE clinics (
    clinic_id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('clinics_sequence'),
    room_number char(10),
    floor INT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE appointments (
    appointment_id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('appointments_sequence'),
    appointment_time TIMESTAMP,
    patient_name VARCHAR(255),
    clinic_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_clinic FOREIGN KEY (clinic_id) REFERENCES clinics(clinic_id),
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);
