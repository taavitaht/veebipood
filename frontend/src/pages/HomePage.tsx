import { useContext, useEffect, useState, type ChangeEvent } from 'react'
import { type Product } from '../models/Product'
import type { OrderRow } from '../models/OrderRow';
import { CartSumContext } from '../context/CartSumContext';
// rfce
// rfc rafce rafc 
function HomePage() {
    //const dbProducts: Product[] = [];
  const [products, setProducts] = useState<Product[]>([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(2);
  const [sort, setSort] = useState("id,asc");
  const {cartSum, setCartSum} = useContext(CartSumContext);

  //uef variant 1: fetch().then().then()
  /*
  useEffect(() => {
    fetch("http://localhost:8080/products")
    .then(res => res.json())
    .then(json => setProducts(json))
    .catch(error => console.log(error))
  }, []);
  */

  //uef variant 2: async await
  useEffect(() => {
    const load = async() => {
      try {
        const res = await fetch(`http://localhost:8080/products?size=${size}&page=${page}&sort=${sort}`);
        const json = await res.json();
        setProducts(json.content);
      } catch (error) {
        console.log(error)
      }
    }
    load();
  }, [page, size, sort]); // Nende muutujate muutumisel tehakse see useEffect uuesti

  function updateSize(e: ChangeEvent<HTMLSelectElement>){
    setSize(Number(e.target.value));
    setPage(0);
  }

  function addToCart(product: Product){
    const cartLS: OrderRow[] = JSON.parse(localStorage.getItem("cart") || "[]");
    const index = cartLS.findIndex(orderRow => orderRow.product.id === product.id);
    if (index >= 0){
      cartLS[index].quantity++;
    } else {
      cartLS.push({"product":product, "quantity": 1});
    }
    localStorage.setItem("cart", JSON.stringify(cartLS));
    setCartSum(cartSum + product.price);
  }

  return (
    <div>
      <button onClick={() => setSort("name,asc")}>Sorteeri A-Z</button>
      <button onClick={() => setSort("name,desc")}>Sorteeri Z-A</button>
      <button onClick={() => setSort("price,asc")}>Sorteeri hind kasvavalt</button>
      <button onClick={() => setSort("price,desc")}>Sorteeri hind kahanevalt</button>
      <button onClick={() => setSort("id,asc")}>Sorteeri vanemad ees</button>
      <button onClick={() => setSort("id,desc")}>Sorteeri uuemad ees</button>

      <br /><br />

      <label>Mitu tk nähtaval</label>
      <select defaultValue={2} onChange={updateSize}>
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
      </select>

        <div className="products">
          {products.map(product => 
          <div key={product.id} className="product">
          <div>{product.name}</div>
          <div>{product.price}€</div>
          <button onClick={() => addToCart(product)}>Lisa ostukorvi</button>
          </div> )}
        </div>

        <button disabled={page === 0} onClick={() => setPage(page - 1)}>Eelmine</button>
        <span>{page + 1}</span>
        <button onClick={() => setPage(page + 1)}>Järgmine</button>
        
    </div>
  )
}

export default HomePage