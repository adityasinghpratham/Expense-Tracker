import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080", // backend URL
  withCredentials: true // critical for session cookies
});

// Optional: global error handling
API.interceptors.response.use(
  response => response,
  error => {
    console.error(error);
    return Promise.reject(error);
  }
);

export default API;
