import { createContext } from "react";

export const AuthContext = createContext({
    loggedIn: false,
    person: {},
    isAdmin: false,
    login: () => {},
    logout: () => {}
    })