import './App.css'
import { Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import Cart from './pages/Cart';
import AddProduct from './pages/AddProduct';
import Menu from './components/Menu';
import NotFound from './pages/NotFound';
import Signup from './pages/Signup';
import Login from './pages/Login';

function App() {

  return (
    <>
      <Menu />

        <Routes>
          <Route path = "/" element={ <HomePage />} />
          <Route path = "/ostukorv" element={<Cart />} />
          <Route path = "/lisa-toode" element={<AddProduct />} />
          <Route path = "/login" element={<Login />} />
          <Route path = "/signup" element={<Signup />} />
          
          <Route path = "/*" element={<NotFound />} />
        </Routes>
    </>
  )
}

export default App
