import { useEffect, useState } from "react";

function useLoadItems(endpoint: string, tokenNeeded: boolean) {
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);

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
                setLoading(false);
                setItems(json);
            } catch (error) {
                console.log(error)
            }
        }
        load();
    }, [endpoint, tokenNeeded]);


    return (
        // 1. {items, loading}
        // 2. [items, loading]
        [items, loading]
    )
}

export default useLoadItems