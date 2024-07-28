import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function Logout() {
  const loggedIn = "true" === localStorage.getItem("loggedIn");
  if (!loggedIn) {
    window.location.href = "/home";
  }

  const [sec, setSec] = useState(5);

  useEffect(() => {
    setTimeout(() => {
      setSec(sec - 1);
    }, 1000);

    setTimeout(() => {
      window.location.href = "/home";
    }, 5000);
  });
  return (
    <>
      {loggedIn ? (
        <div className="text-center">
          <p style={{ fontSize: 50, fontWeight: "bold" }}>
            You have been logged out successfully
          </p>
          <p>
            you will be redirected to homepage in{" "}
            <span style={{ fontWeight: "bold" }}>{sec}</span> seconds, if not
            then click the below button.
          </p>
          <Link to="/home" className="btn btn-primary">
            Go Home
          </Link>
        </div>
      ) : null}
    </>
  );
}

export default Logout;
