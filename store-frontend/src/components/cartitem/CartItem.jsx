/* eslint-disable react/prop-types */
export default function CartItem({ product }) {
  const productName = product.productName;
  const productDesc =
    "This is a wider card with supporting text below as a natural lead-in to additional \
     content. This content is a little bit longer. This is a wider card with supporting \
     text below as a natural lead-in to additional content. This content is a little bit \
     longer.";
  const productPrice = product.productPrice;
  const productQty = product.productQty;

  return (
    <>
      <div className="card mb-4">
        <div className="row g-0 d-flex flex-col">
          {/* 1 */}
          <div className="col-md-2" style={{ flexGrow: 2 }}>
            <img
              src="https://images.unsplash.com/photo-1581235720704-06d3acfcb36f?q=80&w=2960&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
              className="img-fluid rounded-start"
              alt="no-image"
            />
          </div>
          {/* 2 */}
          <div
            className="col-md-7 p-4 d-flex flex-row flex-wrap align-content-between"
            style={{ flexGrow: 8 }}
          >
            <div>
              <h3 className="card-title">{productName}</h3>
              <p className="card-text">{productDesc}</p>
            </div>
            <div className="mt-4">
              <h3 className="card-title">
                ₹ <span style={{ fontWeight: "bolder" }}>{productPrice}</span> *{" "}
                <span style={{ fontWeight: "bolder" }}>
                  <input
                    type="number"
                    min={1}
                    defaultValue={productQty}
                    style={{ width: "20%" }}
                  />
                </span>{" "}
                = ₹{" "}
                <span style={{ fontWeight: "bolder" }}>
                  {productPrice * productQty}
                </span>
              </h3>
            </div>
          </div>
          {/* 3 */}
          <div
            className="col-md-2 d-flex flex-row justify-content-center align-items-center"
            style={{
              width: "10%",
              flexGrow: 1,
            }}
          >
            <button className="btn btn-outline-danger">Remove</button>
          </div>
        </div>
      </div>
    </>
  );
}
