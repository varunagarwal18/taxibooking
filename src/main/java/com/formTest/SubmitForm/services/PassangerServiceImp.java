package com.formTest.SubmitForm.services;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.formTest.SubmitForm.entities.Passangers;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class PassangerServiceImp implements PassangerServices {

	public static final String COL_NAME="customers";
	 public String savePassangerDetails(Passangers passangers) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(passangers.getEmail()).set(passangers);
	        return collectionsApiFuture.get().getUpdateTime().toString();
	    }
	 
	 public Passangers getPassangerDetails(String name) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(name);
	        ApiFuture<DocumentSnapshot> future = documentReference.get();

	        DocumentSnapshot document = future.get();

	        Passangers passangers = null;

	        if(document.exists()) {
	        	passangers = document.toObject(Passangers.class);
	            return passangers;
	        }else {
	            return null;
	        }
	    }

	    public String updatePassangerDetails(Passangers passangers) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(passangers.getName()).set(passangers);
	        return collectionsApiFuture.get().getUpdateTime().toString();
	    }

	    public String deletePassanger(String name) {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(name).delete();
	        return "Document with Passangers ID "+name+" has been deleted";
	    }
}
