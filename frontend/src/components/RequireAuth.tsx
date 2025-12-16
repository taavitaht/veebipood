import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Navigate, Outlet } from "react-router";

function RequireAuth() {
    const { loggedIn, loading } = useContext(AuthContext);

    if(!loggedIn && !loading) {
        return <Navigate to="/login"/>;
    }

  return (
    <Outlet />  // jätab alles originaali mille ümber ta on
  )
}

export default RequireAuth