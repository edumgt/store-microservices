export default function ProductDetails() {
  const loggedIn = "true" === localStorage.getItem("loggedIn");
  if (!loggedIn) {
    window.location.href = "/home";
  }

  const productName = "Demo Product";
  const productDesc =
    "This is a wider card with supporting text below as a natural lead-in to additional \
    content. This content is a little bit longer. This is a wider card with supporting \
    text below as a natural lead-in to additional content. This content is a little bit \
    longer.";
  const productPrice = 235.76;
  const productQty = 32;

  return (
    <>
      {loggedIn ? (
        <div className="card m-4">
          <div className="row g-0">
            <div className="col-md-5">
              <img
                src="https://images.unsplash.com/photo-1581235720704-06d3acfcb36f?q=80&w=2960&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                className="img-fluid rounded-start"
                alt="..."
              />
            </div>
            <div
              className="col-md-7"
              style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                padding: 60,
              }}
            >
              <div style={{ flexGrow: 6 }}>
                <h3 className="card-title">{productName}</h3>
                <p className="card-text">{productDesc}</p>
              </div>
              <div style={{ flexGrow: 1, marginTop: 20 }}>
                <h3 className="card-title" style={{ fontWeight: "bolder" }}>
                  ₹ {productPrice} per unit
                </h3>
                <h5 className="card-text">
                  Total{" "}
                  <span style={{ fontWeight: "bolder" }}>{productQty}</span>{" "}
                  quantity left in inventory.
                </h5>
              </div>
              <div style={{ flexGrow: 0, marginTop: 20 }}>
                <div className="d-grid gap-2 d-md-flex justify-content-md-end">
                  <input
                    className="btn btn-outline-primary me-md-2"
                    type="number"
                    min={1}
                    defaultValue={1}
                  />
                  <button className="btn btn-primary" type="button">
                    Add to Cart
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      ) : null}
    </>
  );
}
