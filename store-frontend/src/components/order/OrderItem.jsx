/* eslint-disable react/prop-types */
export default function OrderItem({ item }) {
  return (
    <>
      <div className="d-flex flex-col justify-content-between align-items-center mt-4">
        <div>
          <p>
            {item.productId}.{" "}
            <span style={{ fontSize: 20, fontWeight: "bold" }}>
              {item.productName}
            </span>{" "}
            - {item.productDesc}
          </p>
        </div>
        <div>
          <p>
            <span style={{ fontSize: 20, fontWeight: "bold" }}>
              ₹ {item.productPrice} * {item.productQty} = ₹{" "}
              {item.productPrice * item.productQty}
            </span>
          </p>
        </div>
      </div>
    </>
  );
}
