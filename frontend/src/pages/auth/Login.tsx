import { useContext, useState } from "react";
import { AuthContext } from "../../context/AuthContext";

function Login() {

  const [loginCredentials, setLoginCredentials] = useState({email: "", password: ""});
  const { login } = useContext(AuthContext);

  function loginHandler(){
    login();
  }

  return (
    <div>
      <label>Email</label><br />
      <input onChange={(e) => setLoginCredentials({ ...loginCredentials, "email": e.target.value })} type="text" /><br />
      <label>Password</label><br />
      <input onChange={(e) => setLoginCredentials({ ...loginCredentials, "password": e.target.value })} type="text" /><br />
      <button onClick={loginHandler}>Login</button>
    </div>
  )
}

export default Login