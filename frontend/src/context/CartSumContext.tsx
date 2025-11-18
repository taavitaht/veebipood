import { createContext } from "react";

// kui keegi impordib seda Contexti, aga temani need ei jõua
export const CartSumContext = createContext({
    cartSum: 0,
    setCartSum: (_newSum: number) => {console.log(_newSum)}
});
