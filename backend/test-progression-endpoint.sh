#!/bin/bash

# Backend Progression Endpoint Test Script
# Make sure your backend is running on port 8100

echo "🔍 Testing Progression Endpoint..."
echo "=================================="

# First, get a JWT token (you'll need to replace with actual login)
echo "1. Login to get JWT token..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "adresseEmail": "abir@gmail.com",
    "motDePasse": "your_password_here"
  }')

echo "Login response: $LOGIN_RESPONSE"

# Extract token (you might need to adjust this based on your response format)
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo "❌ Failed to get JWT token. Please check login credentials."
    echo "   Update the password in this script and try again."
    exit 1
fi

echo "✅ JWT Token obtained: ${TOKEN:0:20}..."

# Test the progression endpoint
echo ""
echo "2. Testing progression endpoint..."

# Test with today's date
TODAY=$(date +%Y-%m-%d)
echo "Testing with today's date: $TODAY"

RESPONSE=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X POST http://localhost:8100/api/progression/enregistrer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d "{
    \"date\": \"$TODAY\",
    \"platIds\": [1, 2],
    \"activiteIds\": [1],
    \"poidsJour\": 70.5,
    \"notes\": \"Test depuis script\"
  }")

HTTP_STATUS=$(echo "$RESPONSE" | grep "HTTP_STATUS" | cut -d: -f2)
BODY=$(echo "$RESPONSE" | sed '/HTTP_STATUS/d')

echo "HTTP Status: $HTTP_STATUS"
echo "Response Body: $BODY"

case $HTTP_STATUS in
    200|201)
        echo "✅ SUCCESS: Progression saved successfully!"
        ;;
    400)
        echo "❌ BAD REQUEST: Check the error message above"
        echo "   Common issues:"
        echo "   - Date outside program range"
        echo "   - Invalid plat/activity IDs"
        echo "   - Program not active"
        ;;
    401)
        echo "❌ UNAUTHORIZED: JWT token issue"
        ;;
    403)
        echo "❌ FORBIDDEN: Permission issue"
        ;;
    500)
        echo "❌ SERVER ERROR: Check backend logs"
        ;;
    *)
        echo "❌ UNEXPECTED STATUS: $HTTP_STATUS"
        ;;
esac

echo ""
echo "3. Testing with different date (tomorrow)..."
TOMORROW=$(date -d "+1 day" +%Y-%m-%d)
echo "Testing with tomorrow's date: $TOMORROW"

RESPONSE2=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X POST http://localhost:8100/api/progression/enregistrer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d "{
    \"date\": \"$TOMORROW\",
    \"platIds\": [1],
    \"activiteIds\": [1]
  }")

HTTP_STATUS2=$(echo "$RESPONSE2" | grep "HTTP_STATUS" | cut -d: -f2)
BODY2=$(echo "$RESPONSE2" | sed '/HTTP_STATUS/d')

echo "HTTP Status: $HTTP_STATUS2"
echo "Response Body: $BODY2"

echo ""
echo "4. Getting progression history..."
HISTORY_RESPONSE=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X GET http://localhost:8100/api/progression/historique \
  -H "Authorization: Bearer $TOKEN")

HISTORY_STATUS=$(echo "$HISTORY_RESPONSE" | grep "HTTP_STATUS" | cut -d: -f2)
HISTORY_BODY=$(echo "$HISTORY_RESPONSE" | sed '/HTTP_STATUS/d')

echo "History HTTP Status: $HISTORY_STATUS"
echo "History Response: $HISTORY_BODY"

echo ""
echo "=================================="
echo "🏁 Test completed!"
echo ""
echo "Next steps:"
echo "1. Check backend logs for detailed error messages"
echo "2. Run the SQL verification script: psql -d BDMobile -f verify-user-program.sql"
echo "3. If issues persist, check the database data and program dates"