import useLoadItems from "../../hooks/useLoadItems";
import type { Order } from "../../models/Order";

function MyOrders() {
  // const [orders, setOrders] = useState([]);
  const [orders, loading]: [Order[], boolean] = useLoadItems("/my-orders", true);

  if (loading) {
    return <div>Loading...</div>
  }

  if (orders.length === 0) {
    return <div>Veel pole tellimusi tehtud</div>
  }

  return (
    <div>
      {orders.map(order =>
        <div key={order.id}>
          <div>{order.id}</div>
          <div>{order.created.toString()}</div>
          <div>{order.total}</div>
          <div>{order.orderRows.map(row =>
            <span key={row.id}>
              <span>| {row.product.name}</span>
              <span>{row.quantity} tk | </span>              
            </span>
          )}
          <br/><br/>
          </div>
        </div>
      )}
    </div>
  )
}

export default MyOrders