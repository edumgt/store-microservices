import Order from "../../components/order/Order";

export default function Orders() {
  const loggedIn = "true" === localStorage.getItem("loggedIn");
  if (!loggedIn) {
    window.location.href = "/home";
  }

  const orders = [
    {
      orderId: "124bghjk13kjb",
      createdOn: "12/12/2025",
      addressTag: "Home - Belagavi",
      orderAmount: 300.0,
      orderStatus: "PLACED",
      modifiedOn: "12/12/2025",
      paymentStatus: "SUCCESS",
      items: [
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 100.0,
          productQty: 12,
        },
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 200.0,
          productQty: 12,
        },
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 300.0,
          productQty: 12,
        },
      ],
    },
    {
      orderId: "124bghjk13kjq",
      createdOn: "12/12/2025",
      addressTag: "Home - Belagavi",
      orderAmount: 300.0,
      orderStatus: "PLACED",
      modifiedOn: "12/12/2025",
      paymentStatus: "SUCCESS",
      items: [
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 100.0,
          productQty: 12,
        },
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 200.0,
          productQty: 12,
        },
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 300.0,
          productQty: 12,
        },
      ],
    },
    {
      orderId: "124bghjk13kjw",
      createdOn: "12/12/2025",
      addressTag: "Home - Belagavi",
      orderAmount: 300.0,
      orderStatus: "PLACED",
      modifiedOn: "12/12/2025",
      paymentStatus: "SUCCESS",
      items: [
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 100.0,
          productQty: 12,
        },
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 200.0,
          productQty: 12,
        },
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 300.0,
          productQty: 12,
        },
      ],
    },
    {
      orderId: "124bghjk13kjr",
      createdOn: "12/12/2025",
      addressTag: "Home - Belagavi",
      orderAmount: 300.0,
      orderStatus: "PLACED",
      modifiedOn: "12/12/2025",
      paymentStatus: "SUCCESS",
      items: [
        {
          productId: "17",
          productName: "Demo Product",
          productDesc: "product sdbks sdbkjsdb skjdbfosd",
          productPrice: 100.0,
          productQty: 12,
        },
      ],
    },
  ];
  return (
    <>
      {loggedIn ? (
        <div className="mt-4" style={{ margin: "5%" }}>
          <p className="p-0 m-0" style={{ fontSize: 50, fontWeight: "bold" }}>
            Orders
          </p>
          {orders.length > 0 ? (
            orders.map((order) => {
              return <Order key={order.orderId} order={order} />;
            })
          ) : (
            <p className="fs-2 text-center mt-4 text-secondary">
              Order not placed yet
            </p>
          )}
        </div>
      ) : null}
    </>
  );
}
