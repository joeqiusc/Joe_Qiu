import React, { useState } from "react";
import "./Login.css";
import { Link, useHistory } from "react-router-dom";
import { auth } from "./firebase";

function Login() {
  const history = useHistory();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const signIn = (e) => {
    e.preventDefault();
<<<<<<< HEAD

    auth
      .signInWithEmailAndPassword(email, password)
      .then((auth) => {
        history.push("/");
      })
      .catch((error) => alert(error.message));
  };

=======
    auth
      .signInWithEmailAndPassword(email, password)
      .then((auth) => {
        history.push("./");
      })
      .catch((error) => alert(error.message));
  };
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
  const register = (e) => {
    e.preventDefault();

    auth
      .createUserWithEmailAndPassword(email, password)
      .then((auth) => {
<<<<<<< HEAD
        // it successfully created a new user with email and password
=======
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
        if (auth) {
          history.push("/");
        }
      })
      .catch((error) => alert(error.message));
  };

  return (
    <div className="login">
      <Link to="/">
        <img
          className="login__logo"
          src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Amazon_logo.svg/1024px-Amazon_logo.svg.png"
<<<<<<< HEAD
          alt=""
        />
      </Link>

=======
        />
      </Link>
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
      <div className="login__container">
        <h1>Sign-in</h1>

        <form>
          <h5>E-mail</h5>
          <input
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <h5>Password</h5>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
<<<<<<< HEAD

=======
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
          <button
            type="submit"
            onClick={signIn}
            className="login__signInButton"
          >
            Sign In
          </button>
        </form>
<<<<<<< HEAD

=======
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
        <p>
          By signing-in you agree to the AMAZON FAKE CLONE Conditions of Use &
          Sale. Please see our Privacy Notice, our Cookies Notice and our
          Interest-Based Ads Notice.
        </p>

        <button onClick={register} className="login__registerButton">
<<<<<<< HEAD
          Create your Amazon Account
=======
          Create your amazon account
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
        </button>
      </div>
    </div>
  );
}

export default Login;
