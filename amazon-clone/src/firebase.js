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
};

const firebaseApp = firebase.initializeApp(firebaseConfig);
const db = firebaseApp.firestore();
const auth = firebase.auth();

export { db, auth };
