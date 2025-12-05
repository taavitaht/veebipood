import { type ReactNode, useState } from "react";
import { AuthContext } from "./AuthContext"
import { useNavigate } from "react-router-dom";

export const AuthContextProvider = ({ children }: { children: ReactNode }) => {
    const [loggedIn, setLoggedIn] = useState(sessionStorage.getItem("token") !== null);
    const [person, setPerson] = useState({});
    const [isAdmin, setAdmin] = useState(false);
    const navigate = useNavigate();

    function login(token: string){
        setLoggedIn(true);
        setPerson({});
        setAdmin(true);
        navigate("/profile");
        sessionStorage.setItem("token", token);
    }

    function logout(){
        setLoggedIn(false);
        setPerson({});
        setAdmin(false);
        navigate("/");
        sessionStorage.removeItem("token");
    }

    return (
        <AuthContext.Provider value={{loggedIn, person, isAdmin, login, logout}}>
            {children}
        </AuthContext.Provider>)
}