/* eslint-disable react/no-unescaped-entities */
export default function Login() {
  const loggedIn = "true" === localStorage.getItem("loggedIn");
  if (loggedIn) {
    window.location.href = "/home";
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
            <label className="form-label">Email address</label>
            <input
              type="email"
              className="form-control"
              id="inputEmail"
              aria-describedby="emailHelp"
              placeholder="example@domain.com"
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
            <button className="w-25 btn btn-primary">Log In</button>
          </div>
        </div>
      ) : null}
    </>
  );
}
