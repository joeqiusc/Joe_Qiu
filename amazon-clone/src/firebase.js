// For Firebase JS SDK v7.20.0 and later, measurementId is optional
import firebase from "firebase/compat/app";
import "firebase/compat/auth";
import "firebase/compat/firestore";
import "firebase/compat/storage";

const firebaseConfig = {
  apiKey: "AIzaSyB9QG2_CD0GBChExlPuTWTLRZB9pkdu7CY",
  authDomain: "clone-43acd.firebaseapp.com",
  projectId: "clone-43acd",
  storageBucket: "clone-43acd.appspot.com",
  messagingSenderId: "858677526420",
  appId: "1:858677526420:web:680a06ce5306d0b7dc9981",
  measurementId: "G-RQHES00E7S",
};

const firebaseApp = firebase.initializeApp(firebaseConfig);
const db = firebaseApp.firestore();
const auth = firebase.auth();

export { db, auth };
