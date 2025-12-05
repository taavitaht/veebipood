import { useEffect, useState } from "react";
import type { Category } from "../../models/Category";
import type { Product } from "../../models/Product";
import { ToastContainer, toast } from "react-toastify";


// rfce
function AddProduct() {
  const [product, setProduct] = useState<Product>({
    "name": "",
    "price": 0,
    "description": "",
    "quantity": 0,
    "category": {
      "id": 1,
      "name": ""
    }
  });

  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await fetch("http://localhost:8080/categories");
        const json = await res.json();
        setCategories(json);
      } catch (error) {
        console.log(error)
      }
    }
    load();
  }, []);

  // function add() {}
  const add = async () => {
    if (product.name === "") {
      toast.error("Cannot add without name");
      return;
    }
    if (product.price <= 0) {
      toast.error("Price cannot be 0 or negative");
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/products", {
        method: "POST",
        body: JSON.stringify(product),
        headers: {
          "Content-Type": "application/json"
        }
      });
      const json = await res.json();
      if (json.message && json.timestamp && json.status) {
        toast.error(json.message);
      } else {
        toast.success("Toode lisatud!");
      }
    } catch (error) {
      toast.error(String(error));
    }
  }

  return (
    <div>
      <div>Ajutine väljakuvamine {JSON.stringify(product)}</div>
      <label>Name</label><br />
      <input onChange={(e) => setProduct({ ...product, "name": e.target.value })} type="text" /><br />
      <label>Price</label><br />
      <input onChange={(e) => setProduct({ ...product, "price": Number(e.target.value) })} type="number" /><br />
      <label>Description</label><br />
      <input onChange={(e) => setProduct({ ...product, "description": e.target.value })} type="text" /><br />
      <label>Quantity</label><br />
      <input onChange={(e) => setProduct({ ...product, "quantity": Number(e.target.value) })} type="number" /><br />
      <label>Category</label><br />
      <select onChange={(e) => setProduct({ ...product, "category": { "id": Number(e.target.value), "name": "" } })}>
        {
          categories.map(category =>
            <option key={category.id} value={category.id}>{category.name}</option>
          )
        }
      </select><br />
      <button onClick={add}>Add</button>

      <ToastContainer />
    </div>
  )
}

export default AddProduct