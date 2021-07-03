import React, { createContext, useContext, useReducer } from "react";

export const StateContext = createContext;

export const StateProvider = ({ reducer, initialState, children }) => (
  <StateContext.StateProvider value={useReducer(reducer, initialState)}>
    {children}
  </StateContext.StateProvider>
);

export const useStateValue = () => useContext(StateContext);
