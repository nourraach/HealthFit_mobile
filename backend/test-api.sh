#!/bin/bash

# Script de test de l'API Backend
# Usage: ./test-api.sh

BASE_URL="http://localhost:8086"

echo "========================================="
echo "Test de l'API Backend"
echo "========================================="
echo ""

# Test 1: Inscription
echo "1. Test d'inscription..."
INSCRIPTION_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/inscription" \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Test",
    "prenom": "User",
    "numTel": "0612345679",
    "adresseEmail": "test@example.com",
    "motDePasse": "password123",
    "dateNaissance": "1990-01-01"
  }')

echo "Réponse inscription:"
echo "$INSCRIPTION_RESPONSE" | jq '.'
echo ""

# Extraire le token
TOKEN=$(echo "$INSCRIPTION_RESPONSE" | jq -r '.token')

if [ "$TOKEN" != "null" ] && [ -n "$TOKEN" ]; then
    echo "✓ Token reçu: ${TOKEN:0:50}..."
    echo ""
else
    echo "✗ Échec de l'inscription"
    echo ""
fi

# Test 2: Authentification
echo "2. Test d'authentification..."
AUTH_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/authentification" \
  -H "Content-Type: application/json" \
  -d '{
    "adresseEmail": "abir@gmail.com",
    "motDePasse": "votre_mot_de_passe"
  }')

echo "Réponse authentification:"
echo "$AUTH_RESPONSE" | jq '.'
echo ""

# Extraire le token
AUTH_TOKEN=$(echo "$AUTH_RESPONSE" | jq -r '.token')

if [ "$AUTH_TOKEN" != "null" ] && [ -n "$AUTH_TOKEN" ]; then
    echo "✓ Token d'authentification reçu: ${AUTH_TOKEN:0:50}..."
    TOKEN="$AUTH_TOKEN"
    echo ""
else
    echo "✗ Échec de l'authentification"
    echo ""
fi

# Test 3: Endpoint protégé
if [ -n "$TOKEN" ] && [ "$TOKEN" != "null" ]; then
    echo "3. Test de l'endpoint protégé /api/programmes/mes-programmes..."
    PROGRAMMES_RESPONSE=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X GET "$BASE_URL/api/programmes/mes-programmes?userId=1" \
      -H "Authorization: Bearer $TOKEN")
    
    HTTP_STATUS=$(echo "$PROGRAMMES_RESPONSE" | grep "HTTP_STATUS" | cut -d: -f2)
    BODY=$(echo "$PROGRAMMES_RESPONSE" | sed '/HTTP_STATUS/d')
    
    echo "Status HTTP: $HTTP_STATUS"
    echo "Réponse:"
    echo "$BODY" | jq '.' 2>/dev/null || echo "$BODY"
    echo ""
    
    if [ "$HTTP_STATUS" = "200" ]; then
        echo "✓ Endpoint protégé accessible avec succès!"
    elif [ "$HTTP_STATUS" = "403" ]; then
        echo "✗ Erreur 403 Forbidden - Le token n'est pas accepté"
    else
        echo "✗ Erreur $HTTP_STATUS"
    fi
else
    echo "3. Impossible de tester l'endpoint protégé (pas de token)"
fi

echo ""
echo "========================================="
echo "Tests terminés"
echo "========================================="
