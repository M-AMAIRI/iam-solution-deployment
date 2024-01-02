docker build . -t simplified-mvn-troubleshooting


# delete cntainer if exist and clean workspace 
$containerName = "java-builder"

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

# build java plugin into container 

docker build . -t simplified-mvn-troubleshooting

docker run --name java-builder -it simplified-mvn-troubleshooting sh -c "mvn clean package" 

# copy the generated plugin ton host folder :
# docker cp java-builder:/usr/src/app/target $PWD
