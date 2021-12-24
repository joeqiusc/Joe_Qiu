export const initialState = {
  basket: [],
  user: null,
};

export const getBasketTotal = (basket) =>
<<<<<<< HEAD
  basket.reduce((amount, item) => item.price + amount, 0);
=======
  basket?.reduce((amount, item) => item.price + amount, 0);

>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
const reducer = (state, action) => {
  // console.log(action);
  switch (action.type) {
    case "ADD_TO_BASKET":
      return {
        ...state,
        basket: [...state.basket, action.item],
      };
    case "REMOVE_FROM_BASKET":
      const index = state.basket.findIndex(
        (basketItem) => basketItem.id === action.id
      );
      let newBasket = [...state.basket];
<<<<<<< HEAD

=======
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
      if (index >= 0) {
        newBasket.splice(index, 1);
      } else {
        console.warn(
          `Cant remove product (id: ${action.id}) as its not in basket!`
        );
      }
<<<<<<< HEAD
      return {
        ...state,
        basket: newBasket,
=======
      return { ...state, basket: newBasket };
    case "SET_USER":
      return {
        ...state,
        user: action.user,
>>>>>>> ab46f2a7efbce4d2a4c695c4624e61ab36245ff9
      };
    default:
      return state;
  }
};

export default reducer;
