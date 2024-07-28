import { Link } from "react-router-dom";

export default function Error() {
  return (
    <>
      <div className="text-center">
        <p style={{ fontSize: 50, fontWeight: "bold", color: "red" }}>
          Error : 404_NOT_FOUND
        </p>
        <Link to="/home" className="btn btn-primary">
          Go Home
        </Link>
      </div>
    </>
  );
}
