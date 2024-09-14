import axios from "axios";
import { BACKEND_SERVER } from "../constants/Constant";

export const loginUser = async (username, password) => {
  const url = BACKEND_SERVER + "/users/login";

  const body = {
    username: username,
    password: password,
  };

  try {
    const res = await axios.post(url, body);
    return res;
  } catch (err) {
    return alert(err.response.data.message);
  }
};
