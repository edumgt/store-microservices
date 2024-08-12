import Pagination from "../../components/pagination/Pagination";
import SearchBar from "../../components/searchbar/SearchBar";
import Products from "../products/Products";

const Home = () => {
  return (
    <div className="home-container p-4">
      <SearchBar />
      <Products />
      <div className="d-flex justify-content-center mt-4">
        <Pagination />
      </div>
    </div>
  );
};

export default Home;
