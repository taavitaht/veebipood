import { useState } from "react";

function Signup() {

  const [person, setPerson] = useState({email: "", password: "", firstName: "", lastName: ""});

  function signupHandler(){
  }

  return (
    <div>
      <label>First name</label><br />
      <input onChange={(e) => setPerson({ ...person, "firstName": e.target.value })} type="text" /><br />
      <label>Last name</label><br />
      <input onChange={(e) => setPerson({ ...person, "lastName": e.target.value })} type="text" /><br />
      <label>Email</label><br />
      <input onChange={(e) => setPerson({ ...person, "email": e.target.value })} type="text" /><br />
      <label>Password</label><br />
      <input onChange={(e) => setPerson({ ...person, "password": e.target.value })} type="text" /><br />
      <button onClick={signupHandler}>Sign up</button>
    </div>
  )
}

export default Signup