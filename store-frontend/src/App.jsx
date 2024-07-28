import { Navigate, Route, Routes } from "react-router-dom";
import Home from "./pages/home/Home";
import Error from "./pages/error/Error";
import Logout from "./pages/logout/Logout";
import Profile from "./pages/profile/Profile";
import Orders from "./pages/orders/Orders";
import Login from "./pages/login/Login";
import Cart from "./pages/cart/Cart";

import NavBar from "./components/navbar/NavBar";
import ProductDetails from "./components/product/ProductDetails";

function App() {
  return (
    <>
      <div className="app">
        <NavBar />
        <Routes>
          <Route exact path="/" element={<Navigate replace to="/home" />} />
          <Route path="/home" element={<Home />} />
          <Route path="/products/:id" element={<ProductDetails />} />
          <Route path="/orders" element={<Orders />} />
          <Route path="/cart" element={<Cart />} />
          <Route path="/profile/:username" element={<Profile />} />
          <Route path="/login" element={<Login />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="*" element={<Error />} />
        </Routes>
      </div>
    </>
  );
}

export default App;
