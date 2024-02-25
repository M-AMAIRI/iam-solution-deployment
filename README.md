# iam-solution-deployment
Creating a tailored Keycloak image for your Identity and Access Management (IAM) solution


clone code source :

```sh
git clone https://github.com/M-AMAIRI/iam-solution-deployment.git
cd iam-solution-deployment
```


- step 1 : Build provider by container .
```sh
./Plugins/keycloak-custom-event-listener/deploy.ps1
```

- step 2 : setup and Deploy your custum IAM .
```sh
./IAM-keycloak/deploy.ps1
```

keycloak URL : https://localhost:8443/
