import React from "react";
import "./Subtotal.css";
import CurrencyFormat from "react-currency-format";
import { useStateValue } from "./StateProvider";
import { getBasketTotal } from "./reducer";
<<<<<<< HEAD
import { useHistory } from "react-router-dom";

function Subtotal() {
  const history = useHistory();
=======
function Subtotal() {
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
  const [{ basket }, dispatch] = useStateValue();
  return (
    <div className="subtotal">
      <CurrencyFormat
        renderText={(value) => (
          <>
            <p>
              {/* Part of the homework */}
<<<<<<< HEAD
              Subtotal ({basket.length} items): <strong>{value}</strong>
=======
              Subtotal ({basket.lenth} items): <strong>{value}</strong>
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
            </p>
            <small className="subtotal__gift">
              <input type="checkbox" /> This order contains a gift
            </small>
          </>
        )}
        decimalScale={2}
<<<<<<< HEAD
        value={getBasketTotal(basket)}
=======
        value={getBasketTotal(basket)} // Part of the homework
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
        displayType={"text"}
        thousandSeparator={true}
        prefix={"$"}
      />
      <button onClick={(e) => history.push("/payment")}>
        Proceed to Checkout
      </button>
    </div>
  );
}

export default Subtotal;
