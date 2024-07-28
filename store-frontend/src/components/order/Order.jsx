import OrderItem from "./OrderItem";

/* eslint-disable react/prop-types */
export default function Order({ order }) {
  return (
    <div key={order.orderId}>
      <div className="card mt-4">
        <div className="card-header d-flex justify-content-between">
          <p style={{ margin: 0 }}>Order id : {order.orderId}</p>
          <p style={{ margin: 0 }}>Placed on : {order.createdOn}</p>
          <p style={{ margin: 0 }}>Address : {order.addressTag}</p>
          <p style={{ margin: 0 }}>Order amount : {order.orderAmount}</p>
        </div>
        <div className="row g-0">
          <div className="col-md-12 p-4">
            <div>
              <p className="card-title">
                Order status:{" "}
                <span style={{ fontWeight: "bold" }}>{order.orderStatus} </span>
                on
                <span style={{ fontWeight: "bold" }}> {order.modifiedOn}</span>
              </p>
              <p className="card-title">
                Payment status:{" "}
                {order.paymentStatus === "SUCCESS" ? (
                  <span style={{ fontWeight: "bold", color: "green" }}>
                    {order.paymentStatus}{" "}
                  </span>
                ) : (
                  <span style={{ fontWeight: "bold", color: "red" }}>
                    {order.paymentStatus}
                  </span>
                )}
              </p>
            </div>
            <div>
              {order.items.map((item, i) => {
                return <OrderItem key={i} item={item} />;
              })}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
