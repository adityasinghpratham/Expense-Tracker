import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axios";

const Login = ({ setUser }) => {
  const [loginData, setLoginData] = useState({ email: "", password: "" });
  const navigate = useNavigate();

  const handleChange = e => setLoginData({ ...loginData, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      // 1️⃣ Login POST request
      await API.post("/api/auth/login", loginData);

      // 2️⃣ Fetch logged-in user info
      const { data } = await API.get("/api/auth/me");
      setUser(data);

      // 3️⃣ Navigate to dashboard
      navigate("/dashboard");
    } catch (err) {
      alert("Login failed: " + (err.response?.data || err.message));
    }
  };

  return (
    <div className="container mt-5">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <input type="email" name="email" placeholder="Email" className="form-control mb-2" required onChange={handleChange} />
        <input type="password" name="password" placeholder="Password" className="form-control mb-2" required onChange={handleChange} />
        <button type="submit" className="btn btn-primary">Login</button>
      </form>
    </div>
  );
};

export default Login;
