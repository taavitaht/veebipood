import { useEffect, useState } from "react";

function useLoadItems<T>(endpoint: string, tokenNeeded: boolean) {
    const [items, setItems] = useState<T[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const load = async () => {
            try {
                let res;
                if (tokenNeeded) {
                    res = await fetch(import.meta.env.VITE_BACKEND_URL + endpoint, {
                        headers: {
                            "Authorization": "Bearer " + sessionStorage.getItem("token")
                        }
                    });
                } else {
                    res = await fetch(import.meta.env.VITE_BACKEND_URL + endpoint);
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
        // 1. {items, loading}, nimetused olulised
        // 2. [items, loading], järjekord oluline
        //[items, loading] as const
        {items, loading}
    )
}

export default useLoadItems