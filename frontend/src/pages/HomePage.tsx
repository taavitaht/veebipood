import { useEffect, useState } from 'react'
import { type Product } from '../models/Product'
// rfce
// rfc rafce rafc 
function HomePage() {
    //const dbProducts: Product[] = [];
  const [products, setProducts] = useState<Product[]>([]);

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
        const res = await fetch("http://localhost:8080/products");
        const json = await res.json();
        setProducts(json);
      } catch (error) {
        console.log(error)
      }
    }
    load();
  }, []);

  return (
    <div>
        {products.map(product => 
        <div key={product.id}>
        <div>{product.name}</div>
        <div>{product.price}</div>
        </div> )}   
    </div>
  )
}

export default HomePage