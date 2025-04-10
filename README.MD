# TV Gateway Management Portal REST API

This is a REST API solution to manage Gateways and attached Peripheral Devices of Tech Venturas' network management system. The solution includes endpoints for creating, reading, updating, and deleting gateways and their associated peripheral devices.

## Tech Stack

- **Programming Language:** Java
- **Framework:** Spring Boot
- **Database:** MySQL

## Solution Requirements

### Gateways
Each gateway has:
- A unique **serial number** (string).
- A **human-readable name** (string).
- An **IPv4 address** (to be validated).
- Multiple associated **peripheral devices** (max 10 devices per gateway).

### Peripheral Devices
Each peripheral device has:
- A unique **UID** (number, auto-generated).
- A **vendor** (string).
- A **date created**.
- A **status**: online/offline.

## Endpoints

### Gateway Endpoints

- **GET** `/gateways`: List all gateways.
- **GET** `/gateways/{id}`: Get details of a specific gateway.
- **POST** `/gateways`: Create a new gateway.
    - **Validation:** Serial number, name, and IPv4 address must be provided and validated.
    - **Limit:** No more than 10 peripheral devices can be associated with a gateway.
- **PUT** `/gateways/{id}`: Update a gateway's information.
- **DELETE** `/gateways/{id}`: Delete a gateway.

### Peripheral Device Endpoints

- **GET** `/devices`: List all peripheral devices.
- **GET** `/devices/{id}`: Get details of a specific peripheral device.
- **POST** `/devices`: Create a new peripheral device associated with a gateway.
- **PUT** `/devices/{id}`: Update a peripheral device's information.
- **DELETE** `/devices/{id}`: Delete a peripheral device.

### Checkout the API Documentation
[TV Gateway API Documentation](https://documenter.getpostman.com/view/35948713/2sB2cX9gip)

## Assumptions

- **IPv4 Address Validation:** Must follow the format `xxx.xxx.xxx.xxx`, with each octet between 0-255. Non-numeric values are rejected.
- **Gateway Serial Number:** Must be unique and non-empty.
- **Peripheral Device Limit:** A maximum of 10 devices per gateway. Adding an 11th will return an error.
- **Peripheral Device UID:** Auto-generated (auto-incremented or UUID). Cannot be manually modified.
- **Peripheral Device Status:** Only "ONLINE" or "OFFLINE" are valid statuses. Any other value will be rejected.
- **Timestamps:** `createdDate` for devices is auto-generated in ISO 8601 format (UTC).
- **Gateway-Device Relationship:** Each device is tied to exactly one gateway.
- **Gateway Deletion:** Deleting a gateway also deletes its devices (cascade delete).
- **API Security:** No authentication or authorization for this assessment.
- **ID Format:** Gateways use UUIDs or DB-generated IDs; devices use auto-incremented integers.
- **Database:** MySQL with automatic schema creation via ORM or initialization scripts.
- **API Response Format:** All responses are in JSON. Errors include message and HTTP status code.
- **Pagination:** List endpoints return all records without pagination.

## How to Run the Project Locally

### Prerequisites

1. **Java 11 or above** installed on your local machine.
2. **MySQL** installed, or you can use Docker to run the database.
3. **Maven** installed.

### Steps to Run

1. **Extract the ZIP file** containing the project.
    - Unzip the `.zip` file to a directory of your choice.
    - You can do this by right-clicking the `.zip` file and selecting "Extract All" or by running the following command in the terminal:
      ```bash
      unzip project.zip -d /path/to/extracted
      ```

2. **Navigate to the project directory**:
      ```bash
      cd /path/to/extracted/project
      ```
   
### Or 

1. **Clone the repository** from GitHub:
   ```bash
   git clone https://github.com/duranginethmini/TV-Gateway-Assignment
   ```
   
### Build and Run the application
      
        mvn spring-boot:run
  
### Create a database if not already created

   ```bash
   CREATE DATABASE dbname;
   ```

### Ensure the database is running
  ```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/dbname
spring.datasource.username=root
spring.datasource.password=password
  ```
## How to Run the test cases
  ```bash
 mvn test
  ```
