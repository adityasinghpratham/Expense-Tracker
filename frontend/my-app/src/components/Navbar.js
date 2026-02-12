// src/components/Navbar.js
import React from "react";
import API from "../api/axios";
// Download PDF of expenses
  const downloadPdf = async () => {
    try {
      const response = await API.get("/api/expenses/export/pdf", { responseType: 'blob' });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'expenses.pdf');
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (err) {
      alert("Failed to download PDF: " + (err.response?.data || err.message));
    }
  };
const Navbar = ({ user, logout }) => {
  return (
    <nav className="navbar">
      <div className="container">
        <h2 className="brand">Expense Tracker</h2>
        {user && (
          <div className="nav-links">

            <button className="btn btn-sm btn-danger logout-btn" onClick={logout}>
               Logout
            </button>
            <button className="btn btn-success" onClick={downloadPdf}>
               Export PDF
            </button>
            <button className="btn btn-success" onClick={downloadPdf}>
               Add Expense
            </button>
            <button className="btn btn-success" onClick={downloadPdf}>
               View Expense
            </button>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
