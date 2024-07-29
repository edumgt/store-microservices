import axios from "axios";

/* eslint-disable react/no-unescaped-entities */
export default function Login() {
  const loggedIn = "true" === localStorage.getItem("loggedIn");
  if (loggedIn) {
    window.location.href = "/home";
  }

  function login() {
    const username = document.getElementById("inputUsername").value;
    const password = document.getElementById("inputPassword").value;

    const apiGatewayPort = "8000";

    const URL = "http://localhost:" + apiGatewayPort + "/api/v1/users/login";

    const body = {
      username: username,
      password: password,
    };

    axios
      .post(URL, body)
      .then((res) => console.log(res.data))
      .catch((err) => console.log(err));
  }

  return (
    <>
      {!loggedIn ? (
        <div
          style={{
            marginLeft: "20%",
            marginRight: "20%",
          }}
          className="mt-4"
        >
          <div className="mb-3">
            <label className="form-label">Username</label>
            <input
              type="text"
              className="form-control"
              id="inputUsername"
              aria-describedby="usernameHelp"
              placeholder="username"
            />
            <div id="emailHelp" className="form-text">
              We'll never share your email with anyone else.
            </div>
          </div>
          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              id="inputPassword"
              placeholder="********"
            />
          </div>
          <div className="text-center mt-4">
            <button className="w-25 btn btn-primary" onClick={() => login()}>
              Log In
            </button>
          </div>
        </div>
      ) : null}
    </>
  );
}
