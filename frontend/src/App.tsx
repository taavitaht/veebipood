import './App.css'
import { Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import Cart from './pages/Cart';
import AddProduct from './pages/admin/AddProduct';
import Menu from './components/Menu';
import NotFound from './pages/NotFound';
import Signup from './pages/auth/Signup';
import Login from './pages/auth/Login';
import { useState } from 'react';
import ManageProducts from './pages/admin/ManageProducts';
import ManageCategories from './pages/admin/ManageCategories';
import ManageAdmins from './pages/admin/ManageAdmins';
import Profile from './pages/auth/Profile';
import MyOrders from './pages/auth/MyOrders';

function App() {
  const [dark, setDark] = useState(localStorage.getItem("isDarkTheme") === "true");

  function updateMode(isDark: boolean) {
    setDark(isDark);
    localStorage.setItem("isDarkTheme", JSON.stringify(isDark));
  }

  return (
    <div className={dark === true ? "dark-mode" : "light-mode"}>
      <Menu />
      <button onClick={() => updateMode(true)}>Dark mode</button>
      <button onClick={() => updateMode(false)}>Light mode</button>

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/ostukorv" element={<Cart />} />


        <Route path="/lisa-toode" element={<AddProduct />} />
        <Route path="/halda-admine" element={<ManageAdmins />} />
        <Route path="/halda-kategooriaid" element={<ManageCategories />} />
        <Route path="/halda-tooteid" element={<ManageProducts />} />


        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/orders" element={<MyOrders />} />
        <Route path="/profile" element={<Profile />} />

        <Route path="/*" element={<NotFound />} />
      </Routes>
    </div>
  )
}

export default App
