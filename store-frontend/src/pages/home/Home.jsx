import Alert from "../../components/alert/Alert";
import Pagination from "../../components/pagination/Pagination";
import SearchBar from "../../components/searchbar/SearchBar";
import Products from "../products/Products";

const Home = () => {
  return (
    <div className="home">
      <div className="home-container m-4">
        <Alert />
        <SearchBar />
        <Products />
        <div className="d-flex justify-content-center mt-4">
          <Pagination />
        </div>
      </div>
    </div>
  );
};

export default Home;
