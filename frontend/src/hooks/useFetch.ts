/* eslint-disable @typescript-eslint/no-explicit-any */
//import { get } from "http";
import { useTranslation } from "react-i18next";
import { toast } from "react-toastify";

function useFetch() {
    const { t } = useTranslation(); // kui poleks seda rida, oleks util kaustas

    async function makeQuery(endpoint: string, apiMethod: string, payload: any, message: string) {
        try {
            const res = await fetch(import.meta.env.VITE_BACKEND_URL + endpoint, {
                method: apiMethod,
                body: JSON.stringify(payload),
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("token"),
                    "Content-Type": "application/json"
                }
            });
            if (res.status === 403) {
                toast.error(getErrorMesage("not-enough-rights"));
                return;
            }
            const json = await res.json();
            if (json.message && json.timestamp && json.status) {
                toast.error(getErrorMesage(json.message));
            } else {
                toast.success(t("success." + message));
            }
        } catch (error) {
            console.log(error)
        }
    }


    function getErrorMesage(message: string) {
        const errorMessage = t("error." + message);
        if (errorMessage.startsWith("error.")) {  // Ei saanud tõlkida
            return t("error.generic");  // Üldine veateade
        }
        return errorMessage;  // Sai tõlkida, backendist tulnud tõlgitud veateade
    }


    return (
        makeQuery
    )
}



export default useFetch
