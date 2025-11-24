# Trotti-UL API Test Suite Documentation

## Overview
This comprehensive test suite validates all functionalities of the Trotti-UL electric scooter rental service API. The system supports account management, ride permits, trip operations, station management, and maintenance workflows.

## System Architecture
- **Server**: Java 21 with Jetty servlet container
- **Port**: 8080 (exposed via Docker)
- **Authentication**: JWT tokens with 60-minute expiration
- **Roles**: STUDENT, EMPLOYEE, TECHNICIAN, ADMIN
- **Permissions**: Role-based access control

## Test Categories

### 1. System Health
- **Health Check**: Validates server status and availability
- **Expected Response**: `{"status": "UP"}`

### 2. Account Management
Tests user registration, authentication, and role-based access.

#### User Roles & Permissions:
- **STUDENT**: Can create accounts, make trips, request maintenance
- **EMPLOYEE**: Same as student + additional employee permissions
- **TECHNICIAN**: Can perform maintenance, relocate scooters, manage transfers
- **ADMIN**: Can create company accounts (ADMIN/TECHNICIAN roles)

#### Test Scenarios:
- Create student/employee accounts (public endpoint)
- Admin login with initial credentials
- Create technician account (admin-only)
- JWT token validation and storage
- Duplicate account prevention (409 Conflict)

### 3. Order Management
Tests the complete order lifecycle from cart management to payment confirmation.

#### Order Flow:
1. **Get Current Order**: `GET /order` - Retrieve current order (may be empty)
2. **Add Items**: `PUT /order` - Add ride permit items to cart
3. **Remove Items**: `DELETE /order/{itemId}` - Remove specific items
4. **Clear Order**: `DELETE /order` - Remove all items
5. **Confirm Order**: `POST /order/confirm` - Process payment and confirm

#### Item Structure:
- **maximumDailyTravelTime**: Minutes (minimum 10)
- **session**: Academic session (e.g., "A25", "H26")
- **billingFrequency**: "MONTHLY" or "PER_TRIP"

#### Payment Information:
- **cardNumber**: Credit card number (13-19 digits)
- **cardHolderName**: Cardholder name
- **expirationDate**: YYYY-MM format
- **cvv**: 3-4 digit security code (always required)

#### Business Rules:
- Only STUDENT role can manage orders
- Requires CART_MODIFICATION and ORDER_CONFIRM permissions
- Payment method can be saved for future use
- Orders generate ride permits upon successful payment

### 4. Ride Permits
Tests ride permit management and retrieval.

#### Endpoints:
- `GET /ride-permit` - Get user's ride permits
- `GET /ride-permit/{id}` - Get specific ride permit

### 5. Trip Management
Tests the complete trip lifecycle from unlock to completion.

#### Trip Flow:
1. **Request Unlock Code**: `POST /trips/{ridePermitId}/unlock-code`
2. **Start Trip**: `POST /trips/start` with unlock code and location
3. **End Trip**: `POST /trips/end` with destination location

#### Required Data:
- Valid ride permit ID
- Unlock code from previous step
- Station location (VACHON, PEPS, etc.)
- Slot number

### 6. Station Management
Tests station operations and slot availability.

#### Available Stations:
- VACHON (15 slots)
- PEPS (25 slots)  
- DESJARDINS (20 slots)
- VANDRY (15 slots)
- MYRAND (12 slots)
- PYRAMIDE (15 slots)
- CASAULT (14 slots)
- PLACE_STE_FOY (15 slots)

#### Endpoints:
- `GET /stations/{location}/slots/available`
- `GET /stations/{location}/slots/occupied`
- `POST /stations/maintenance/request` (all authenticated users)

### 7. Maintenance Operations (Technician Only)
Tests maintenance workflow and station management.

#### Maintenance Flow:
1. **Start Maintenance**: `POST /stations/maintenance/start`
2. **Perform Operations**: Station becomes unavailable for regular use
3. **End Maintenance**: `POST /stations/maintenance/end`

#### Business Rules:
- Only technicians can start/end maintenance
- Station must not already be in maintenance (409 Conflict)
- Station must be in maintenance to end it (409 Conflict)

### 8. Transfer Operations (Technician Only)
Tests scooter relocation between stations.

#### Transfer Flow:
1. **Initiate Transfer**: `POST /stations/transfers/initiate`
   - Specify source station and slot numbers
   - Station must be in maintenance mode
2. **Unload Scooters**: `POST /stations/transfers/{transferId}/unload`
   - Specify destination station and slots
   - Complete the transfer operation

### 9. Error Handling
Tests various error scenarios and proper HTTP status codes.

#### Error Scenarios:
- **401 Unauthorized**: Invalid credentials, missing tokens
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Invalid station locations, non-existent resources
- **409 Conflict**: Duplicate accounts, invalid state transitions
- **400 Bad Request**: Invalid request data

## Authentication Setup

### Initial Admin Account
The system comes with a pre-configured admin account:
- **Email**: admin@ulaval.ca
- **Password**: ChangeMe123!@#
- **IDUL**: admin00

### JWT Token Management
- Tokens expire after 60 minutes (configurable via TOKEN_EXPIRATION_DURATION)
- Include token in Authorization header: `Authorization: {jwt_token}`
- Tokens are automatically extracted and stored in collection variables

## Running the Tests

### Prerequisites
1. Start the Trotti-UL application:
   ```bash
   ./run.sh  # Unix/macOS
   # or
   .\run.bat  # Windows
   ```

2. Ensure the application is running on http://localhost:8080

### Test Execution Order
The tests are designed to run sequentially:

1. **System Health** - Verify server is running
2. **Account Management** - Create accounts and authenticate
3. **Order Management** - Test cart and payment flow
4. **Ride Permits** - Test permit retrieval
5. **Trip Management** - Test trip lifecycle
6. **Station Management** - Test station operations
7. **Transfer Operations** - Test scooter relocation
8. **Error Handling** - Validate error responses
9. **Station Locations** - Test all station endpoints

### Collection Variables
The test suite uses collection variables to maintain state:
- `baseUrl`: API base URL (http://localhost:8080)
- `adminToken`: JWT token for admin user
- `studentToken`: JWT token for student user
- `technicianToken`: JWT token for technician user
- `employeeToken`: JWT token for employee user
- `ridePermitId`: Active ride permit ID
- `transferId`: Active transfer ID
- `unlockCode`: Generated unlock code

## Expected Test Results

### Success Scenarios
- Account creation: 201 Created with Location header
- Authentication: 200 OK with JWT token
- Station queries: 200 OK with array response
- Maintenance requests: 204 No Content
- Valid operations: 200 OK

### Error Scenarios
- Invalid credentials: 401 Unauthorized
- Insufficient permissions: 403 Forbidden
- Resource not found: 404 Not Found
- Conflict states: 409 Conflict
- Invalid data: 400 Bad Request

## Business Logic Validation

### Account Management
- Email and IDUL uniqueness enforced
- Role-based permission validation
- Password complexity requirements

### Trip Management
- Ride permit validation required
- Unlock code generation and validation
- Station and slot availability checks

### Maintenance Operations
- State transition validation (maintenance on/off)
- Permission-based access control
- Conflict prevention for concurrent operations

### Transfer Operations
- Source station must be in maintenance
- Slot availability validation
- Transfer state management

## Troubleshooting

### Common Issues
1. **Server not responding**: Verify application is running on port 8080
2. **Authentication failures**: Check token expiration (60 minutes)
3. **Permission errors**: Verify user role and required permissions
4. **Station not found**: Use valid station codes from the list above
5. **Maintenance conflicts**: Check station maintenance state

### Debug Tips
- Check server logs for detailed error messages
- Verify JWT token format and expiration
- Ensure proper Content-Type headers (application/json)
- Validate request body structure matches API expectations

## API Documentation
The application includes Swagger UI documentation available at:
`http://localhost:8080/swagger-ui.html` (if configured)

## Environment Configuration
Key environment variables (config.env):
- `TOKEN_EXPIRATION_DURATION`: JWT token lifetime
- `SMTP_*`: Email configuration for notifications
- `INITIAL_ADMIN_*`: Initial admin account setup