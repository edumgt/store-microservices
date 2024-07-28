import { Link } from "react-router-dom";

export default function NavBar() {
  const loggedIn = "true" === localStorage.getItem("loggedIn");

  const title = "Praveen's Store";

  return (
    <>
      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <a className="navbar-brand" href="/">
            {title}
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div
            className="collapse navbar-collapse justify-content-between"
            id="navbarNav"
          >
            <div className="d-flex align-items-center">
              <ul className="navbar-nav">
                <li className="nav-item">
                  <Link className="nav-link" aria-current="page" to="/">
                    Home
                  </Link>
                </li>
                {loggedIn ? (
                  <>
                    <li className="nav-item">
                      <Link
                        className="nav-link"
                        aria-current="page"
                        to="/orders"
                      >
                        Orders
                      </Link>
                    </li>
                    <li className="nav-item">
                      <Link className="nav-link" aria-current="page" to="/cart">
                        Cart
                      </Link>
                    </li>
                  </>
                ) : null}
              </ul>
            </div>
            <div className="d-flex align-items-center">
              {loggedIn ? (
                <>
                  <Link className="nav-link" aria-current="page" to="/profile">
                    Profile
                  </Link>
                  <Link className="btn btn-outline-danger ms-4" to="/logout">
                    Log Out
                  </Link>
                </>
              ) : (
                <Link className="btn btn-primary" to="/login">
                  Log In
                </Link>
              )}
            </div>
          </div>
        </div>
      </nav>
    </>
  );
}
