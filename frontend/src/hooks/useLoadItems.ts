import { useEffect, useState } from "react";

function useLoadItems(endpoint: string, tokenNeeded: boolean) {
    const [items, setItems] = useState([]);

    useEffect(() => {
        const load = async () => {
            try {
                let res;
                if (tokenNeeded) {
                    res = await fetch("http://localhost:8080" + endpoint, {
                        headers: {
                            "Authorization": "Bearer " + sessionStorage.getItem("token")
                        }
                    });
                } else {
                    res = await fetch("http://localhost:8080" + endpoint);
                }
                const json = await res.json();
                setItems(json);
            } catch (error) {
                console.log(error)
            }
        }
        load();
    }, [endpoint, tokenNeeded]);


    return (
        items
    )
}

export default useLoadItems