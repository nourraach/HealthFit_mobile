# PowerShell script to test the API
Write-Host "Testing API endpoints..." -ForegroundColor Green

# 1. Register a new user
Write-Host "`n1. Registering user..." -ForegroundColor Yellow
$registerBody = @{
    nom = "Admin"
    prenom = "User"
    adresseEmail = "admin@example.com"
    motDePasse = "admin123"
    numTel = "1234567890"
    dateNaissance = "1990-01-01"
} | ConvertTo-Json

try {
    $registerResponse = Invoke-RestMethod -Uri "http://localhost:8089/api/auth/inscription" -Method POST -ContentType "application/json" -Body $registerBody
    Write-Host "✅ User registered successfully!" -ForegroundColor Green
    Write-Host "Response: $($registerResponse | ConvertTo-Json)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Registration failed: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Error details: $responseBody" -ForegroundColor Red
    }
}

# 2. Login to get token
Write-Host "`n2. Logging in..." -ForegroundColor Yellow
$loginBody = @{
    adresseEmail = "admin@example.com"
    motDePasse = "admin123"
} | ConvertTo-Json

try {
    $loginResponse = Invoke-RestMethod -Uri "http://localhost:8089/api/auth/authentification" -Method POST -ContentType "application/json" -Body $loginBody
    Write-Host "✅ Login successful!" -ForegroundColor Green
    $token = $loginResponse.token
    Write-Host "Token: $token" -ForegroundColor Cyan
    
    # 3. Test authenticated endpoint
    Write-Host "`n3. Testing authenticated endpoint..." -ForegroundColor Yellow
    $headers = @{
        "Authorization" = "Bearer $token"
        "Content-Type" = "application/json"
    }
    
    $programmes = Invoke-RestMethod -Uri "http://localhost:8089/api/programmes" -Method GET -Headers $headers
    Write-Host "✅ Got programmes: $($programmes.Count) items" -ForegroundColor Green
    
} catch {
    Write-Host "❌ Login failed: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Error details: $responseBody" -ForegroundColor Red
    }
}

Write-Host "`nDone! You can now use the API." -ForegroundColor Green