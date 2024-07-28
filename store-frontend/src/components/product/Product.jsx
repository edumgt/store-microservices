/* eslint-disable react/prop-types */
export default function Product({ product }) {
  const loggedIn = "true" === localStorage.getItem("loggedIn");

  const productId = product.productId;
  const productName = product.productName;
  const productDesc = product.productDesc;

  return (
    <div className="card">
      <img
        src="https://images.unsplash.com/photo-1581235720704-06d3acfcb36f?q=80&w=2960&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        className="card-img-top"
        alt="no-image"
      />
      <div className="card-body">
        <h5 className="card-title">{productName}</h5>
        <p className="card-text">{productDesc}</p>
      </div>
      {loggedIn ? (
        <ul className="list-group list-group-flush">
          <button
            type="submit"
            className="btn btn-primary"
            style={{
              borderRadius: 0,
              borderBottomLeftRadius: 5,
              borderBottomRightRadius: 5,
            }}
            onClick={() => {
              window.location.href = "/products/" + productId;
            }}
          >
            More Info
          </button>
        </ul>
      ) : null}
    </div>
  );
}
