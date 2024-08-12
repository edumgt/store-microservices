import { useState } from "react";

export default function SearchBar() {
  let [category, setCategory] = useState("Select Category");

  return (
    <form className="d-flex" role="search">
      <div className="btn-group">
        <button
          type="button"
          className="btn btn-primary dropdown-toggle"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          {category}
        </button>
        <ul className="dropdown-menu">
          <li>
            <span
              className="dropdown-item"
              onClick={() => setCategory("Mobiles")}
            >
              Mobiles
            </span>
          </li>
          <li>
            <span
              className="dropdown-item"
              onClick={() => setCategory("Laptops")}
            >
              Laptops
            </span>
          </li>
        </ul>
      </div>
      <input
        className="form-control ms-4"
        type="search"
        placeholder="Search for Products"
        aria-label="Search"
        style={{ marginLeft: 10 }}
      />
      <button className="btn btn-outline-success ms-4">Search</button>
      <button className="btn btn-outline-primary ms-4">Filters</button>
    </form>
  );
}
