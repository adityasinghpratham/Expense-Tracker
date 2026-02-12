import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axios";

const Signup = () => {
  const [user, setUser] = useState({ fullName: "", email: "", password: "" });
  const navigate = useNavigate();

  const handleChange = e => setUser({ ...user, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      await API.post("/api/auth/signup", user);
      alert("Signup successful! Please login.");
      navigate("/login");
    } catch (err) {
      alert("Signup failed: " + (err.response?.data || err.message));
    }
  };

  return (
    <div className="container mt-5">
      <h2>Signup</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="fullName" placeholder="Full Name" className="form-control mb-2" required onChange={handleChange} />
        <input type="email" name="email" placeholder="Email" className="form-control mb-2" required onChange={handleChange} />
        <input type="password" name="password" placeholder="Password" className="form-control mb-2" required onChange={handleChange} />
        <button type="submit" className="btn btn-primary">Signup</button>
      </form>
    </div>
  );
};

export default Signup;
