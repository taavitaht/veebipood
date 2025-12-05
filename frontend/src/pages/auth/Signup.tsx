import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";


function Signup() {
  const [person, setPerson] = useState({email: "", password: "", firstName: "", lastName: ""});

  async function signupHandler(){
    if (person.email === "") {
      toast.error("Cannot add without email");
      return;
    }
    if (person.password === "") {
      toast.error("Cannot add without password");
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/signup", {
        method: "POST",
        body: JSON.stringify(person),
        headers: {
          "Content-Type": "application/json"
        }
      });
      const json = await res.json();
      if (json.message && json.timestamp && json.status) {
        toast.error(json.message);
      } else {
        toast.success("Kasutaja lisatud!");
      }
    } catch (error) {
      toast.error(String(error));
    }
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
      <input onChange={(e) => setPerson({ ...person, "password": e.target.value })} type="password" /><br />
      <button onClick={signupHandler}>Sign up</button>
      <ToastContainer />
    </div>
  )
}

export default Signup