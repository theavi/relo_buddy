$ErrorActionPreference = "Stop"

$modules = "eureka-server", "rlb-notification", "rlb-oc"

foreach ($module in $modules) {
    Write-Host " Building $module..."
    Set-Location $module
    mvn clean package -DskipTests
    Set-Location ..
}

Write-Host " Building Docker images..."
docker-compose build

Write-Host " All services built successfully!"
