# Sistema de Agendamento

A RESTful appointment scheduling system built with Spring Boot 4.0.0 and Java 21.

## Overview

Sistema de Agendamento is a backend service for managing appointments with conflict detection, status tracking, and user-based scheduling. The system prevents double-booking and provides comprehensive appointment lifecycle management.

## Features

- **Create Appointments**: Schedule new appointments with title, description, start/end times, and user assignment
- **Update Appointments**: Modify existing appointment details
- **Cancel Appointments**: Mark appointments as cancelled
- **Complete Appointments**: Mark appointments as concluded (only after end time)
- **Conflict Detection**: Automatically prevents overlapping appointments for the same user
- **Status Management**: Track appointment lifecycle (AGENDADO, CANCELADO, CONCLUIDO)
- **Validation**: Ensures valid date ranges and prevents scheduling conflicts

## Technology Stack

- **Java 21**
- **Spring Boot 4.0.0**
  - Spring Data JPA
  - Spring Web MVC
  - Spring Validation
- **PostgreSQL** - Database
- **Flyway** - Database migration management
- **Lombok** - Reduce boilerplate code
- **Maven** - Build and dependency management

## Prerequisites

- Java 21 or higher
- PostgreSQL database
- Maven 3.x
- Environment variables:
  - `POSTGRES_USER` - PostgreSQL username
  - `POSTGRES_PASSWORD` - PostgreSQL password

## Database Setup

The application uses PostgreSQL with Flyway for schema management. The database will be automatically created and migrated on startup.

**Database name**: `sistema-agendamento`

**Default connection**: `jdbc:postgresql://localhost:5432/sistema-agendamento`

### Database Schema

The main table `tb_agendamento` includes:
- `id` - Primary key (auto-generated)
- `titulo` - Appointment title (max 120 characters)
- `descricao` - Appointment description (text)
- `data_inicio` - Start date/time
- `data_fim` - End date/time
- `status` - Appointment status (AGENDADO, CANCELADO, CONCLUIDO)
- `usuario` - User assigned to appointment (max 80 characters)
- `criado_em` - Creation timestamp
- `atualizado_em` - Last update timestamp (auto-updated via trigger)

## Getting Started

1. **Clone the repository**

2. **Set environment variables**:
   ```bash
   export POSTGRES_USER=your_username
   export POSTGRES_PASSWORD=your_password
   ```

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

   Or on Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

4. **Build the project**:
   ```bash
   ./mvnw clean install
   ```

## API Endpoints

Base URL: `/api/v1/agendamentos`

### Get Appointment by ID
```http
GET /api/v1/agendamentos/{id}
```

**Response**: `200 OK`
```json
{
  "id": 1,
  "titulo": "Meeting",
  "descricao": "Team sync meeting",
  "dataInicio": "2025-12-10T10:00:00",
  "dataFim": "2025-12-10T11:00:00",
  "status": "AGENDADO",
  "usuario": "john.doe",
  "criadoEm": "2025-12-10T09:00:00Z",
  "atualizadoEm": "2025-12-10T09:00:00Z"
}
```

### Create New Appointment
```http
POST /api/v1/agendamentos
```

**Request Body**:
```json
{
  "titulo": "Meeting",
  "descricao": "Team sync meeting",
  "dataInicio": "2025-12-10T10:00:00",
  "dataFim": "2025-12-10T11:00:00",
  "usuario": "john.doe"
}
```

**Response**: `201 Created` with `Location` header

### Update Appointment
```http
PUT /api/v1/agendamentos/{id}
```

**Request Body**:
```json
{
  "titulo": "Updated Meeting",
  "descricao": "Updated description",
  "dataInicio": "2025-12-10T14:00:00",
  "dataFim": "2025-12-10T15:00:00"
}
```

**Response**: `200 OK`

### Cancel Appointment
```http
PUT /api/v1/agendamentos/appointment/cancel/{id}
```

**Response**: `200 OK` with updated appointment (status: CANCELADO)

### Conclude Appointment
```http
PUT /api/v1/agendamentos/appointment/conclude/{id}
```

**Response**: `200 OK` with updated appointment (status: CONCLUIDO)

**Note**: Can only conclude appointments after their end time has passed.

## Business Rules

1. **Date Validation**: End date must be after start date
2. **Conflict Detection**: Users cannot have overlapping appointments with status AGENDADO
3. **Conclusion Validation**: Appointments can only be concluded after their end time
4. **Status Transitions**:
   - New appointments start as AGENDADO
   - Can be cancelled (CANCELADO) or concluded (CONCLUIDO)

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK` - Successful request
- `201 Created` - Appointment created successfully
- `400 Bad Request` - Invalid input or business rule violation
- `404 Not Found` - Appointment not found
- `409 Conflict` - Scheduling conflict detected

## Project Structure

```
src/main/java/com/sistemaagendamento/
├── controller/          # REST API endpoints
├── dto/                # Data Transfer Objects
├── entities/           # JPA entities
├── mapper/             # Entity-DTO mappers
├── repository/         # Data access layer
└── service/            # Business logic
```

## Development

The project includes Spring DevTools for hot reloading during development.

### Running Tests
```bash
./mvnw test
```

