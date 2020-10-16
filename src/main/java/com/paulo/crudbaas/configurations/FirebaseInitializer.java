package com.paulo.crudbaas.configurations;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitializer {

	private final String FIREBASE_KEY = "./serviceAccountKey.json";
	private final String FIREBASE_DATABASE_URL = "https://crud-baas.firebaseio.com";
	@PostConstruct
	public void initialize() {
		try {
			FileInputStream serviceAccount = new FileInputStream(FIREBASE_KEY);

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl(FIREBASE_DATABASE_URL).build();

			FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}