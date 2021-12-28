const functions = require("firebase-functions");
const express = require("express");
const cors = require("cors");
const { request, response } = require("express");
const stripe = require("stripe")(
  "sk_test_51KBLq7HkzbJkK5P9XnQFIrc7l2TBy0UbwF6ChKcgQA9WftMBXkjo2qiuIYJFEjne6Y5ZpgnvPYBnWgB2r1Vg1iZj00qKAPtsTm"
);
const app = express();
app.use(cors({ origin: true }));
app.use(express.json());
app.get("/", (request, response) => response.status(200).send("Hello, World!"));
app.post("/payments/create", async (request, response) => {
  const total = request.query.total;
  console.log("Payment Request Recieved Oyeah ", total);
  const paymentIntent = await stripe.paymentIntents.create({
    amount: total,
    currency: "usd",
  });
  response.status(201).send({
    clientSecret: paymentIntent.client_secret,
  });
});

exports.api = functions.https.onRequest(app);

//http://localhost:5001/clone-43acd/us-central1/api
