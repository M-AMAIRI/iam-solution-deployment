

mkdir Configuration/providers
mkdir Configuration/certs

docker cp java-builder:/usr/src/app/target/custom-event-listener.jar $PWD/Configuration/providers


openssl.exe req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -keyout Configuration/certs/keycloak-server.key -out Configuration/certs/keycloak-server.crt

docker build . -t iam-keycloak 


$containerName = "mykeycloak"

# Get the container ID by name (whether running or not)
$containerId = docker ps -aq -f "name=$containerName"

if ($containerId) {
    # Container exists
    Write-Host "Container $containerName found with ID $containerId."

    # Check if the container is not running
    $isRunning = docker inspect -f '{{.State.Running}}' $containerId
    if ($isRunning -eq $null) {
        Write-Host "Container $containerName is not running. Deleting..."
        docker rm $containerId
        Write-Host "Container $containerName deleted."
    } else {
        Write-Host "Container $containerName is not running. Deleting..."
        docker rm $containerId
        Write-Host "Container $containerName deleted."
    }
} else {
    Write-Host "Container $containerName not found."
}



docker run --name mykeycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -p 8443:8443 iam-keycloak start --optimized

