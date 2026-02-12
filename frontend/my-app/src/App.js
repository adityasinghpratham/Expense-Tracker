// src/App.js
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import API from "./api/axios";
import "./App.css"
import Signup from "./pages/Signup";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const { data } = await API.get("/auth/me");
        setUser(data);
      } catch {}
    };
    fetchUser();
  }, []);

  return (
    <Router>
      <Routes>
        <Route path="/signup" element={<Signup />} />
        <Route path="/login" element={<Login setUser={setUser} />} />
        <Route
          path="/dashboard"
          element={user ? <Dashboard user={user} setUser={setUser} /> : <Navigate to="/login" />}
        />
        <Route path="/" element={<Navigate to="/dashboard" />} />
      </Routes>
    </Router>
  );
}

export default App;
