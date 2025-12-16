import i18n from "i18next";
import { initReactI18next } from "react-i18next";

// the translations
// (tip move them in a JSON file and import them,
// or even better, manage them separated from your code: https://react.i18next.com/guides/multiple-translation-files)
const resources = {
  en: {
    translation: {
      "menu": {
        "cart": "Cart",
        "add-product": "Add Product",
        "login": "Login",
        "signup": "Signup"
      },
      "success": {
        "updated-profile": "Successfully updated profile",
        "updated-password": "Password updated successfully",
        "added-product": "Product added successfully"
      },
      "error": {
        "Email cannot be empty": "Email cannot be empty",
        "Email is not valid": "Email is not valid",
        "Email already taken": "Email already taken",
        "not-enough-rights": "You do not have enough rights to perform this action",
        "generic": "Unexpected error. Try again later."
      }
    }
  },
  et: {
    translation: {
      "menu": {
        "cart": "Ostukorv",
        "add-product": "Lisa toode",
        "login": "Logi sisse",
        "signup": "Registreeru"
      },
      "success": {
        "updated-profile": "Edukalt profiil uuendatud",
        "updated-password": "Parool edukalt muudetud",
        "added-product": "Toode edukalt lisatud"
      },
      "error": {
        "Email cannot be empty": "Email ei saa olla tühi",
        "Email is not valid": "Email ei ole õigel kujul",
        "Email already taken": "Email on juba võetud",
        "not-enough-rights": "Pole piisavalt õigusi",
        "generic": "Juhtus ootamatu viga. Proovi hiljem uuesti."
      }

    }
  }
};

i18n
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    resources,
    lng: localStorage.getItem("language") || "et", // language to use, more information here: https://www.i18next.com/overview/configuration-options#languages-namespaces-resources
    // you can use the i18n.changeLanguage function to change the language manually: https://www.i18next.com/overview/api#changelanguage
    // if you're using a language detector, do not define the lng option

    interpolation: {
      escapeValue: false // react already safes from xss
    }
  });

export default i18n;