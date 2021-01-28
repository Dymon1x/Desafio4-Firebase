package br.com.digitalhouse.firebaselogin_desafio4.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

val db: FirebaseFirestore = FirebaseFirestore.getInstance()
val cr: CollectionReference = db.collection("Games")