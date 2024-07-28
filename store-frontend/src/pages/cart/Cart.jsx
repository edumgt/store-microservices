import CartItem from "../../components/cartitem/CartItem";

export default function Cart() {
  const loggedIn = "true" === localStorage.getItem("loggedIn");
  if (!loggedIn) {
    window.location.href = "/home";
  }

  const items = [
    {
      productId: "17",
      productName: "Demo Product",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334.0,
      productQty: 12,
    },
    {
      productId: "17",
      productName: "Demo Product",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334.0,
      productQty: 12,
    },
    {
      productId: "17",
      productName: "Demo Product",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334.0,
      productQty: 12,
    },
  ];
  return (
    <>
      {loggedIn ? (
        <>
          <div className="m-4" style={{ margin: "5%" }}>
            <p className="p-0 m-0" style={{ fontSize: 50, fontWeight: "bold" }}>
              Cart Items
            </p>
            {items.length > 0 ? (
              items.map((product) => {
                return <CartItem key={product.productId} product={product} />;
              })
            ) : (
              <p className="fs-2 text-center mt-4 text-secondary">
                Cart is empty
              </p>
            )}
          </div>
          {items.length > 0 ? (
            <div className="d-flex justify-content-center mb-5">
              <button className="btn btn-primary">Procced to Checkout</button>
            </div>
          ) : null}
        </>
      ) : null}
    </>
  );
}
