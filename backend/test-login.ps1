# Test login with detailed error reporting
Write-Host "Testing user login..." -ForegroundColor Green

$body = @{
    adresseEmail = "test2@example.com"
    motDePasse = "password123"
} | ConvertTo-Json

Write-Host "Request body:" -ForegroundColor Yellow
Write-Host $body -ForegroundColor Cyan

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8089/api/auth/authentification" -Method POST -ContentType "application/json" -Body $body
    Write-Host "✅ Login successful!" -ForegroundColor Green
    Write-Host ($response | ConvertTo-Json) -ForegroundColor Cyan
} catch {
    Write-Host "❌ Login failed!" -ForegroundColor Red
    Write-Host "Status Code: $($_.Exception.Response.StatusCode)" -ForegroundColor Red
    
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Error Response: $responseBody" -ForegroundColor Red
    }
    
    Write-Host "Full Exception: $($_.Exception.Message)" -ForegroundColor Red
}