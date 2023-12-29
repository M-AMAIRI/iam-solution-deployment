










docker build . -t simplified-mvn-troubleshooting

docker run --name java-builder -it simplified-mvn-troubleshooting sh -c "mvn clean package" 

$env:TargetPath="C:\Users\amair\OneDrive\Bureau\coinnov product\Installation Service Keycloak\Tutorials Videos\video 2\plugindev\target" 

$env:Idcontainer=$(docker ps -aq -f name=java-builder -f status=exited)

echo $env:Idcontainer

echo $env:TargetPath

docker cp %Idcontainer%:/usr/src/app/target %TargetPath%
