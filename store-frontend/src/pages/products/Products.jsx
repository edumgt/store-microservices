import Product from "../../components/product/Product";

export default function Products() {
  // const products = [];
  const products = [
    {
      productId: "2895021",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895022",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895023",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895024",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895025",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895026",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895027",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895028",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895029",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
    {
      productId: "2895030",
      productName: "product1",
      productDesc: "product sdbks sdbkjsdb skjdbfosd",
      productPrice: 334,
      productQty: 12,
    },
  ];

  return (
    <div
      className="mt-4"
      style={{
        display: "grid",
        gridTemplateColumns: "repeat(auto-fill, minmax(250px, 1fr))",
        gap: "20px",
      }}
    >
      {products.length > 0
        ? products.map((product) => {
            return <Product key={product.productId} product={product} />;
          })
        : null}
    </div>
  );
}
