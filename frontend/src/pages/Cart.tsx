import { useState } from "react"
import type { Product } from "../models/Product";

function Cart() {
  const [products, setProducts] = useState<Product[]>(JSON.parse(localStorage.getItem("cart") || "[]"));

  function empty() {
    setProducts([]);
    localStorage.setItem("cart", "[]");
  }

  return (
    <div>
      <button onClick={empty}>Empty</button>
      
      {products.map(product =>
        <div key={product.id}>
          <div>{product.name}</div>
          <div>{product.price}€</div>
        </div>
      )}
      
    </div>
  )
}

export default Cart