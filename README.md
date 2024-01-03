# iam-solution-deployment
Creating a tailored Keycloak image for your Identity and Access Management (IAM) solution


clone code source :

```sh
git clone https://github.com/M-AMAIRI/iam-solution-deployment.git
cd iam-solution-deployment
```

if you want to try keycloak without providers :
```sh
./IAM-keycloak/deploy-0.ps1
```


Build provider by container :

- step 1 :
```sh
./Plugins/keycloak-custom-event-listener/deploy.ps1
```

- step 2 :
setup and Deploy your custum IAM : 
```sh
./IAM-keycloak/deploy.ps1
```

keycloak URL : https://localhost:8443/
