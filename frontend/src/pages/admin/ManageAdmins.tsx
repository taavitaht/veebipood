import { useEffect, useState } from "react";
import useLoadItems from "../../hooks/useLoadItems"
import type { Person } from "../../models/Person";

function ManageAdmins() {

  const [persons, setPersons] = useState<Person[]>([]);
  const dbPersons: Person[] = useLoadItems("/persons", true);

  useEffect(() => {
    setPersons(dbPersons);
  }, [dbPersons]);

  function changeAdmin(person: Person) {
    fetch(`http://localhost:8080/change-admin?id=${person.id}`, {
      method: "PATCH",
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
    .then(res => res.json())
    .then(json => {
      setPersons(json);
    })
  }

  return (
    <div>
      {persons.map(person =>
        <div key={person.id}>
          <div>{person.id}</div>
          <div>{person.firstName} {person.lastName}</div>
          <div>{person.email}</div>
          <div>{person.role}</div>
          <button disabled={person.role === "SUPERADMIN"} onClick={() => changeAdmin(person)}>Change role</button>
        </div>
      )}
    </div>
  )
}

export default ManageAdmins