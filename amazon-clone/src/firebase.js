<<<<<<< HEAD
import firebase from "firebase/compat/app";
import "firebase/compat/auth";
import "firebase/compat/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyAUSJDdXcpO-qHJytqoRZKgCRqXe7jcPZo",
  authDomain: "clone-4f8fe.firebaseapp.com",
  projectId: "clone-4f8fe",
  storageBucket: "clone-4f8fe.appspot.com",
  messagingSenderId: "429401974645",
  appId: "1:429401974645:web:564ebf07bdf84324eed53c",
  measurementId: "G-VHELB3NB71",
=======
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
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
};

const firebaseApp = firebase.initializeApp(firebaseConfig);
const db = firebaseApp.firestore();
const auth = firebase.auth();

export { db, auth };
