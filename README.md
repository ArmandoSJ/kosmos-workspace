<h1 align="center">Medical Clinic Services</h1>

### Setting Up
To use the system, it is necessary to start the services in a specific order. The process is detailed below:

1. Start the Config Server
* This service provides configuration for the system's microservices.

2. Start Clinic Manager Server
* This module provides the services

**Important Note:** To start the services, it is necessary to configure the appropriate profile in the `application.yml` file of each project. The profiles are described below:

**Local Profile:** When using this profile, the service will automatically use the H2 database, so it will not be necessary to start the PostgreSQL database container.

**Dev Profile:** If this profile is configured, you will need to follow the steps outlined to create and start the database container.

### Steps to setting up the database

1. **Create the container:** To create the container, run the following command in the terminal: `docker-compose up -d`. Once the creation is complete, go to the following pgAdmin URL in your browser: [http://localhost:8080/login?next=/](http://localhost:8080/login?next=/).

2. **Enter credentials from the .env file:** Before proceeding, you need to enter your credentials from the `.env` file to access pgAdmin. Look for the `PGA_EMAIL` and `PGA_PASSWORD` variables and enter them during login.

3. **Create the server in pgAdmin:**  In the pgAdmin interface, go to the "Servers" section. Right-click to open a menu, then select "Register" and "Server."

4. **Server configuration:** In the server configuration window, fill in the following details:

   #### General
   - **name:** postgres (name of the PostgreSQL container)

   #### Connection
   - **Host name/address:** postgres (name of the PostgreSQL container)
   - **Post:** 5432
   - **Maintenance database:** kosmos_db
   - **username:** kosmos
   - **password:** YXNhbGF6YXJqQGdtYWlsLmNvbToqR

After completing the details, click "Save" to establish the connection.

**Note:** Remember that these details are stored in the `.env`, file, where your credentials were previously configured.

---
Services

# Doctors

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/doctors?page=0&size=5' \
  --header 'Content-Type: application/json' \
```


```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/doctors/1?=' \
  --header 'Content-Type: application/json' \
```

```shell
curl --request POST \
  --url http://localhost:8081/api/v1/doctors \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "Armando",
	"lastNamePaternal": "Salazar",
	"lastNameMaternal":"Jauregui",
	"specialty": "Medicina Interna"
}'
```

```shell
curl --request PUT \
  --url http://localhost:8081/api/v1/doctors/1 \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "Armando",
	"lastNamePaternal": "Salazar",
	"lastNameMaternal":"Jauregui",
	"specialty": "Medicina Interna"
}'
```
# Clinics

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/clinics?page=0&size=5' \
  --header 'Content-Type: application/json' \
```

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/clinics/1?=' \
  --header 'Content-Type: application/json' \
```

```shell
curl --request POST \
  --url http://localhost:8081/api/v1/clinics \
  --header 'Content-Type: application/json' \
  --data '{
	"roomNumber": "23",
	"floor": 2
}'
```

```shell
curl --request PUT \
  --url http://localhost:8081/api/v1/clinics/1 \
  --header 'Content-Type: application/json' \
  --data '{
	"roomNumber": "24",
	"floor": 2
}'
```

# Appointments

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/appointments?page=0&size=5' \
  --header 'Content-Type: application/json' \
```

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/appointments/2?=' \
  --header 'Content-Type: application/json' \
```

```shell
curl --request POST \
  --url http://localhost:8081/api/v1/appointments \
  --header 'Content-Type: application/json' \
  --data '{
  "clinic_id": 1,
  "doctor_id": 1,
  "appointmentTime": "2025-05-10T15:30:00",
  "patientName": "Juan Pérez"
}'
```

```shell
curl --request PUT \
  --url http://localhost:8081/api/v1/appointments/2 \
  --header 'Content-Type: application/json' \
  --data '{
  "clinic_id": 1,
  "doctor_id": 1,
  "appointmentTime": "2025-05-10T15:30:00",
  "patientName": "Juan Pérez"
}'
```

**Consultar por numero de habitacion**

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/appointments/room/14?=' \
  --header 'Content-Type: application/json' \
```
**Consultar por nombre del doctor**

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/appointments/doctor/Armando?=' \
  --header 'Content-Type: application/json' \
```

**Consultar por fecha**

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/appointments/date/2025-05-10T16:30:00?=' \
  --header 'Content-Type: application/json' \
```

**Saber cuántas citas tengo hoy o mañana**

```shell
curl --request GET \
  --url 'http://localhost:8081/api/v1/appointments/by-doctor?doctorName=Armando&date=2025-05-10T16%3A30%3A00' \
  --header 'Content-Type: application/json' \
```

**Cancel an appointment**

```shell
curl --request DELETE \
--url 'http://localhost:8081/api/v1/appointments/1?=' \
--header 'Content-Type: application/json' \
```

### Appointment Scheduling Cases

#### Caso inicial valores correcto

```shell
curl --request POST \
  --url http://localhost:8081/api/v1/appointments \
  --header 'Content-Type: application/json' \
  --data '{
    "clinic_id": 1,
    "doctor_id": 1,
    "appointmentTime": "2025-05-10T16:30:00",
    "patientName": "Juan Pérez"
  }'
```

#### No se puede agendar cita en un mismo consultorio a la misma hora.

```shell
curl --request POST \
  --url http://localhost:8081/api/v1/appointments \
  --header 'Content-Type: application/json' \
  --data '{
    "clinic_id": 1,
    "doctor_id": 1,
    "appointmentTime": "2025-05-10T16:30:00",
    "patientName": "Jose Armando"
  }'
```


#### El doctor ya tiene una cita a esa hora.

```shell
curl --request POST \
  --url http://localhost:8081/api/v1/appointments \
  --header 'Content-Type: application/json' \
  --data '{
    "clinic_id": 3,
    "doctor_id": 1,
    "appointmentTime": "2025-05-11T16:30:00",
    "patientName": "Jose Armando"
  }'
```
#### El paciente tiene otra cita demasiado cercana.

```shell
curl --request POST \
  --url http://localhost:8081/api/v1/appointments \
  --header 'Content-Type: application/json' \
  --data '{
    "clinic_id": 3,
    "doctor_id": 2,
    "appointmentTime": "2025-05-11T16:30:00",
    "patientName": "Jose Armando"
  }'
```



