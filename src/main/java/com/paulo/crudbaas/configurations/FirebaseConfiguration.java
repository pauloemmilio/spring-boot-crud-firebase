package com.paulo.crudbaas.configurations;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfiguration {

    @Bean
    public Firestore getDb() {
        return FirestoreClient.getFirestore();
    }

}
