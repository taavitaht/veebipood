import { useEffect, useState } from "react";
// import { useTranslation } from "react-i18next";
import { ToastContainer } from "react-toastify";
import useFetch from "../../hooks/useFetch";


function Profile() {

  const [person, setPerson] = useState({ email: "", password: "", firstName: "", lastName: "" });
  const [passwordCredentials, setPasswordCredentials] = useState({});
  // const { t } = useTranslation();
  const backendQuery = useFetch();

  useEffect(() => {
    async function load() {
      try {
        const res = await fetch("http://localhost:8080/person", {
          headers: {
            "Authorization": "Bearer " + sessionStorage.getItem("token")
          }
        });
        const json = await res.json();
        setPerson(json);
        setPasswordCredentials({ "id": json.id })
        console.log(json);
      } catch (error) {
        console.log(error)
      }
    }
    load();
  }, []);

  // async function updateProfile() {
  //   try {
  //     const res = await fetch("http://localhost:8080/persons", {
  //       method: "PUT",
  //       body: JSON.stringify(person),
  //       headers: {
  //         "Authorization": "Bearer " + sessionStorage.getItem("token"),
  //         "Content-Type": "application/json"
  //       }
  //     });
  //     const json = await res.json();
  //     if (json.message && json.timestamp && json.status) {
  //       toast.error(getErrorMesage(json.message));
  //     } else {
  //       toast.success(t("profile.success"));
  //     }
  //   } catch (error) {
  //     console.log(error)
  //   }
  // }

  // async function changePassword() {
  //   try {
  //     const res = await fetch("http://localhost:8080/update-password", {
  //       method: "PATCH",
  //       body: JSON.stringify(passwordCredentials),
  //       headers: {
  //         "Authorization": "Bearer " + sessionStorage.getItem("token"),
  //         "Content-Type": "application/json"
  //       }
  //     });
  //     const json = await res.json();
  //     if (json.message && json.timestamp && json.status) {
  //       toast.error(getErrorMesage(json.message));
  //     } else {
  //       toast.success(t("profile.success"));
  //     }
  //   } catch (error) {
  //     console.log(error)
  //   }
  // }

  // function getErrorMesage(message: string) {
  //   const errorMessage = t("error." + message);
  //   if (errorMessage.startsWith("error.")) {  // Ei saanud tõlkida
  //     return t("error.generic");  // Üldine veateade
  //   }
  //   return errorMessage;  // Sai tõlkida, backendist tulnud tõlgitud veateade
  // }


  return (
    <div>
      <label>First name</label><br />
      <input defaultValue={person.firstName} onChange={(e) => setPerson({ ...person, "firstName": e.target.value })} type="text" /><br />
      <label>Last name</label><br />
      <input defaultValue={person.lastName} onChange={(e) => setPerson({ ...person, "lastName": e.target.value })} type="text" /><br />
      <label>Email</label><br />
      <input defaultValue={person.email} onChange={(e) => setPerson({ ...person, "email": e.target.value })} type="text" /><br />
      <button onClick={() => backendQuery("/persons", "PUT", person)}>Update profile</button>
      <ToastContainer />
      <br /><br /><br />

      <label>Old Password</label><br />
      <input type="password"
        onChange={(e) => setPasswordCredentials({
          ...passwordCredentials,
          "oldPassword": e.target.value
        })} /><br />
      <label>New Password</label><br />
      <input type="password"
        onChange={(e) => setPasswordCredentials({
          ...passwordCredentials,
          "newPassword": e.target.value
        })} /><br />
      <label>Confirm new Password</label><br />
      <input type="password"
        onChange={(e) => setPasswordCredentials({
          ...passwordCredentials,
          "confirmPassword": e.target.value
        })} /><br />
      <button onClick={() => backendQuery("/update-password", "PATCH", passwordCredentials)}>Muuda parool</button>
    </div>
  )
}

export default Profile