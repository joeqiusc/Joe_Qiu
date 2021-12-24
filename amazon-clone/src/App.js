import React, { useEffect } from "react";
import "./App.css";
import Header from "./Header";
import Home from "./Home";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Checkout from "./Checkout";
<<<<<<< HEAD
import { useStateValue } from "./StateProvider";
import { auth } from "./firebase";
import Login from "./Login";
=======
import Login from "./Login";
import { auth } from "./firebase";
import { useStateValue } from "./StateProvider";
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9

function App() {
  const [{}, dispatch] = useStateValue();

  useEffect(() => {
<<<<<<< HEAD
    // will only run once when the app component loads...

    auth.onAuthStateChanged((authUser) => {
      console.log("THE USER IS >>> ", authUser);

      if (authUser) {
        // the user just logged in / the user was logged in

        dispatch({
          type: "SET_USER",
          user: authUser,
        });
      } else {
        // the user is logged out
        dispatch({
          type: "SET_USER",
          user: null,
        });
=======
    auth.onAuthStateChanged((authUser) => {
      console.log("the user is >>> ", authUser);
      if (authUser) {
        dispatch({ type: "SET_USER", user: authUser });
      } else {
        dispatch({ type: "SET_USER", user: null });
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
      }
    });
  }, []);

  return (
    <Router>
      <div className="app">
<<<<<<< HEAD
        <Header />
=======
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
        <Switch>
          <Route path="/login">
            <Login />
          </Route>
          <Route path="/checkout"><<<<<<< HEAD<Checkout /></Route>
          <Route path="/">
=======
            <Header />
            <Checkout />
          </Route>
          <Route path="/">
            <Header />
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
            <Home />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
