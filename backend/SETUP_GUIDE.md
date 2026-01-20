# Backend Setup Guide

## Prerequisites
- PostgreSQL installed and running
- Java 17 or higher
- Maven

## Step-by-Step Setup

### 1. Database Setup
```bash
# Connect to PostgreSQL as superuser
psql -U postgres

# Run the setup script
\i setup-database.sql

# Exit PostgreSQL
\q
```

### 2. Update Configuration
The `application.properties` file has been updated with:
- Database URL: `jdbc:postgresql://localhost:5432/BDMobile`
- Username: `postgres` 
- Password: `postgres` (change this to match your PostgreSQL setup)
- Server port: `8089`

**Important**: Update the database password in `application.properties` to match your PostgreSQL installation.

### 3. Start the Backend
```bash
# Navigate to backend directory
cd backend-projet-fitness-mobile-main

# Run with Maven
./mvnw spring-boot:run

# Or on Windows
mvnw.cmd spring-boot:run
```

### 4. Register Test Users
After the backend is running, register test users:
```powershell
# Run the registration script
.\register-test-users.ps1
```

### 5. Test the API
```powershell
# Test authentication and endpoints
.\test-api.ps1
```

## Common Issues and Solutions

### Issue 1: Database Connection Failed
- **Cause**: PostgreSQL not running or wrong credentials
- **Solution**: 
  - Start PostgreSQL service
  - Update username/password in `application.properties`
  - Ensure database `BDMobile` exists

### Issue 2: Port Already in Use
- **Cause**: Port 8089 is occupied
- **Solution**: Change `server.port` in `application.properties`

### Issue 3: Authentication Failed
- **Cause**: Using old test users with plain text passwords
- **Solution**: Use the registration endpoint to create users with properly encoded passwords

### Issue 4: Tables Not Created
- **Cause**: Database permissions or Hibernate configuration
- **Solution**: 
  - Check database user has CREATE privileges
  - Verify `spring.jpa.hibernate.ddl-auto=update` in properties

## API Endpoints

### Authentication
- `POST /api/auth/inscription` - Register new user
- `POST /api/auth/authentification` - Login user
- `POST /api/auth/authenticate` - Alternative login endpoint

### Protected Endpoints (require JWT token)
- `GET /api/programmes` - Get fitness programs
- `GET /api/plats` - Get meal plans
- `GET /api/user/profile` - Get user profile

## Testing
1. Register a user via `/api/auth/inscription`
2. Login via `/api/auth/authentification` to get JWT token
3. Use the token in Authorization header: `Bearer <token>`
4. Access protected endpoints

## Mobile App Configuration
Update the mobile app's API base URL to: `http://localhost:8089/api/`