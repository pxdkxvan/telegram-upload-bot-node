# Node Service

The **Node Service** is the core processing component of the application. It consumes messages from RabbitMQ queues sent by the Dispatcher Service, processes commands using a Spring State Machine, and forwards responses back to the Dispatcher Service via RabbitMQ. The service integrates with PostgreSQL for data persistence and uses OpenFeign for external API calls, with monitoring provided by Spring Boot Actuator.

## Table of Contents
- [Overview](#overview)
- [Supported Profiles](#supported-profiles)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Command Processing](#command-processing)
- [Actuator Endpoints](#actuator-monitoring)
- [CI/CD Integration](#cicd-integration)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Node Service handles the following tasks:
- Consuming messages from RabbitMQ queues sent by the Dispatcher Service.
- Identifying and processing commands (single-phase or two-phase) using a Spring State Machine.
- Storing or retrieving data from a PostgreSQL database as needed.
- Sending processed results to RabbitMQ queues for response handling by the Dispatcher Service.

### Supported Profiles

The service supports the following profiles, defined in `application.yml`:
- `telegram`: Configures Telegram-related settings.
- `rabbitmq`: Sets up RabbitMQ messaging.
- `postgres`: Enables PostgreSQL database connectivity.
- `openfeign`: Configures OpenFeign clients for external API calls.
- `actuator`: Enables Spring Boot Actuator for monitoring.

## Features

- **Command Processing**: Processes bot commands using a Spring State Machine to handle single-phase and two-phase workflows.
- **RabbitMQ Integration**: Consumes and produces messages via RabbitMQ queues.
- **PostgreSQL Support**: Persists data to a PostgreSQL database.
- **OpenFeign Clients**: Facilitates communication with external APIs.
- **Actuator Monitoring**: Provides endpoints for health checks and metrics.

## Prerequisites

To run the service, ensure you have:
- Java 21 or higher
- Spring Boot 3.4.x
- Access to a RabbitMQ server
- Access to a PostgreSQL database
- Gradle for dependency management

## Setup

Follow these steps to set up and run the service:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/pxdkxvan/telegram-upload-bot-node.git
   ```

2. **Configure the following variables in a `.env` file**:
   - `NODE_NAME`: Unique name for the Node Service.
   - `NODE_PORT`: Port for the Node Service.
   - `APPLICATION_PROTOCOL`: The protocol (http or https).
   - `CONFIG_HOST`: Domain or IP for the Config Service (e.g., `config`).
   - `CONFIG_PORT`: Port for the Config Service (e.g., `8888`).
   - `OPENFEIGN_NAME`: Name for the OpenFeign client.
   - `OPENFEIGN_HOST`: Domain or IP for the OpenFeign service (e.g., `api`).
   - `OPENFEIGN_PORT`: Port for the OpenFeign target service.
   - `RABBITMQ_HOST`: Domain or IP for RabbitMQ (e.g., `rabbitmq`).
   - `RABBITMQ_PORT`: RabbitMQ port (e.g., `5672`).
   - `RABBITMQ_USER`: Username for the RabbitMQ connection (e.g., `guest`).
   - `RABBITMQ_PASSWORD`: Password for the RabbitMQ connection (e.g., `guest`).
   - `POSTGRES_HOST`: Domain or IP for PostgreSQL (e.g., `postgres`).
   - `POSTGRES_PORT`: PostgreSQL port (e.g., `5432`).
   - `POSTGRES_USER`: Username for the PostgreSQL database connection (e.g., `postgres`).
   - `POSTGRES_PASSWORD`: Password for the PostgreSQL database connection (e.g., `mysecretpassword`).
   - `ACTUATOR_ENDPOINT`: Base path for Actuator endpoints (e.g., `/actuator`).

   **Note**: The Telegram bot token is configured via the Config Service and encrypted.

3. **Build the Project**:
   ```bash
   ./gradlew build
   ```

4. **Run the Service**:
   ```bash
   ./gradlew bootRun
   ```

## Command Processing

- **Message Consumption**: The service listens to RabbitMQ queues populated by the Dispatcher Service.
- **Command Identification**: A Spring State Machine determines whether a command is single-phase (one-step) or two-phase (requires additional steps).
- **Processing**: Commands are processed based on their type, potentially interacting with the PostgreSQL database or external APIs via OpenFeign.
- **Response Generation**: Processed results are sent to a RabbitMQ response queue, which the Dispatcher Service consumes to reply to the Telegram chat.

**Note**: Specific command types and their behaviors are documented in the main project README.

## Actuator Monitoring

The service exposes the following Actuator endpoints:
- `/actuator/health`: Checks service, RabbitMQ, and database connection status.
- `/actuator/info`: Provides service information.
- `/actuator/metrics`: Displays performance and system metrics.
- `/actuator/loggers`: Allows viewing and managing logging levels.

## CI/CD Integration

The service can be integrated into a CI/CD pipeline:
- Include the service startup in your deployment scripts.
- Ensure RabbitMQ, PostgreSQL, and external API configurations are set in the target environment.

## Security

- Protect RabbitMQ, PostgreSQL, and OpenFeign credentials using environment variables or a secrets manager.
- Restrict access to Actuator endpoints with Spring Security if needed.
- Use secure connections (e.g., SSL) for RabbitMQ and database communication.

## Contributing

Contributions are welcome! Please submit a pull request or open an issue to discuss ideas or fixes.

## License

This project is licensed under the [MIT License](LICENSE).