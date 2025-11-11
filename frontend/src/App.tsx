import { useState } from 'react'
import './App.css'

function App() {
  const dbProducts = [
    {
        "id": 5,
        "name": "Sprite",
        "description": "Karastusjook",
        "price": 1.2,
        "quantity": 100,
        "category": null
    },
    {
        "id": 2,
        "name": "Sprite",
        "description": "Karastusjook",
        "price": 1.2,
        "quantity": 100,
        "category": null
    },
    {
        "id": 3,
        "name": "Fanta",
        "description": "Karastusjook",
        "price": 2.4,
        "quantity": 100,
        "category": null
    },
    {
        "id": 6,
        "name": "Pepsi",
        "description": "Karastusjook",
        "price": 1.2,
        "quantity": 100,
        "category": null
    },
    {
        "id": 7,
        "name": "iPhone",
        "description": "Nutitelefon",
        "price": 1600.0,
        "quantity": 100,
        "category": {
            "id": 4,
            "name": "alcohol"
        }
    },
    {
        "id": 8,
        "name": "Nokia",
        "description": "Nutitelefon",
        "price": 1600.0,
        "quantity": 100,
        "category": {
            "id": 5,
            "name": "clothing"
        }
    },
    {
        "id": 9,
        "name": "SonyEricsson",
        "description": "Nutitelefon",
        "price": 1000.0,
        "quantity": 100,
        "category": {
            "id": 3,
            "name": "electronics"
        }
    }
]

  const [products, setProducts] = useState(dbProducts)

  return (
    <>
      {products.map(product => <div>{product.name}</div>)}
    </>
  )
}

export default App
