package com.cevher.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

import java.util.Map;

public class CustomEventListenerProvider
        implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);

    private final KeycloakSession session;
    private final RealmProvider model;

    public CustomEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }



    @Override
    public void onEvent(Event event) {


        if (EventType.REGISTER.equals(event.getType())) {

            event.getDetails().forEach((key, value) -> log.debugf("%s : %s",key, value));

            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel user = this.session.users().getUserById(realm,event.getUserId());
        }
        
    }




    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

        if (ResourceType.USER.equals(adminEvent.getResourceType())
                && ( OperationType.CREATE.equals(adminEvent.getOperationType()) || OperationType.UPDATE.equals(adminEvent.getOperationType()) || OperationType.DELETE.equals(adminEvent.getOperationType()) ) ){
            RealmModel realm = this.model.getRealm(adminEvent.getRealmId());

            String userId = adminEvent.getResourcePath().substring(adminEvent.getResourcePath().lastIndexOf("/")+1);

            UserModel user = this.session.users().getUserById(realm,userId);
            
            UserModel AdminUser = this.session.users().getUserById(realm,adminEvent.getAuthDetails().getUserId());
            StringBuilder sb = new StringBuilder();

            if (AdminUser != null) {

                if (user != null) {
                    if ( OperationType.UPDATE.equals(adminEvent.getOperationType()) )
                    {
                        sb.append("Registering the user (USER="); 
                        sb.append(user.getUsername());
                        sb.append(", ID= ");
                        sb.append(userId);
                        sb.append(" ) in REALM= ");
                        sb.append(realm.getName());
                        sb.append(" by admin account (USER= ");
                        sb.append(AdminUser.getUsername());
                        sb.append(" , ID= ");
                        sb.append(adminEvent.getAuthDetails().getUserId());
                        sb.append(") , Operation=");
                        sb.append(adminEvent.getOperationType());

                    }
                    if ( OperationType.CREATE.equals(adminEvent.getOperationType()) )
                    {
                        sb.append("Updating the user (USER="); 
                        sb.append(user.getUsername());
                        sb.append(", ID= ");
                        sb.append(userId);
                        sb.append(" ) in REALM= ");
                        sb.append(realm.getName());
                        sb.append(" by admin account (USER= ");
                        sb.append(AdminUser.getUsername());
                        sb.append(" , ID= ");
                        sb.append(adminEvent.getAuthDetails().getUserId());
                        sb.append(") , Operation=");
                        sb.append(adminEvent.getOperationType());
                    } 
                    }

                    if ( OperationType.DELETE.equals(adminEvent.getOperationType()) )
                    {
                        sb.append("Deleting the user (ID=");
                        sb.append(userId);
                        sb.append(" ) in REALM= ");
                        sb.append(realm.getName());
                        sb.append(" by admin account (USER= ");
                        sb.append(AdminUser.getUsername());
                        sb.append(" , ID= ");
                        sb.append(adminEvent.getAuthDetails().getUserId());
                        sb.append(") , Operation=");
                        sb.append(adminEvent.getOperationType());   
                    }

                    log.info(sb.toString());
  
            }

       

        }
    }


    @Override
    public void close() {}


}


