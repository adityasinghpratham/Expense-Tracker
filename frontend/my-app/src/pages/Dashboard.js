import React, { useEffect, useState } from "react";
import API from "../api/axios";
import Navbar from "../components/Navbar";
import ExpenseForm from "../components/ExpenseForm";
import ExpenseList from "../components/ExpenseList";
import { useNavigate } from "react-router-dom";
const Dashboard = ({ user, setUser }) => {
  const [expenses, setExpenses] = useState([]);
  const [editExpense, setEditExpense] = useState(null);
  const [searchText, setSearchText] =  useState("");
  const navigate = useNavigate();
  // Fetch all expenses
  const fetchExpenses = async () => {
    try {
      const { data } = await API.get("/api/expenses");
      setExpenses(data);
    } catch (err) {
      alert("Failed to fetch expenses: " + (err.response?.data || err.message));
    }
  };

  useEffect(() => {
    fetchExpenses();
  }, [searchText]);

  // Add or update expense
  const addOrUpdateExpense = async (expense) => {
    try {
      if (expense.id) {
        await API.put(`/api/expenses/${expense.id}`, expense);
      } else {
        await API.post("/api/expenses", expense);
      }
      setEditExpense(null);
      fetchExpenses();
    } catch (err) {
      alert("Error: " + (err.response?.data || err.message));
    }
  };

  // Delete expense
  const deleteExpense = async (id) => {
    if (window.confirm("Are you sure you want to delete this expense?")) {
      try {
        await API.delete(`/api/expenses/${id}`);
        fetchExpenses();
      } catch (err) {
        alert("Failed to delete expense: " + (err.response?.data || err.message));
      }
    }
  };

  // Logout user
  const logout = async () => {
    try {
      await API.post("/auth/logout");
      setUser(null);
    } catch (err) {

    }
    navigate("/login");
  };
 const handleSearch = async () => {
   try {
     const response = await API.get(
       `/api/expenses/search`,
       { params: { keyword: searchText }, withCredentials: true}

     );
     setExpenses(response.data);
   } catch (error) {
     console.error("Search error:", error);
   }
 };


  return (
    <div>
      <Navbar user={user} logout={logout} />

      <div className="container mt-3">
        <div className="d-flex justify-content-between align-items-center mb-3">


        </div>



        <h3>Welcome back! This is your personal expense management hub, where you can effortlessly keep track of all your spending, categorize your expenses, and gain insights into your financial habits. Add new expenses as they happen, review your past spending to identify patterns, and use the analytics tools to make smarter financial decisions. You can also export detailed reports whenever you need them, giving you a clear and organized view of your finances. Your journey to smarter money management starts here — stay consistent, stay aware, and take control of your financial future.</h3>
        {/* Expense Form */}
        <ExpenseForm onSubmit={addOrUpdateExpense} expenseToEdit={editExpense} />
          <div style={{ display: "flex", gap: "10px", marginBottom: "20px" }}>
            <input
              type="text"
              value={searchText}
              placeholder="Search by title, category, or description..."
              onChange={(e) => setSearchText(e.target.value)}
              style={{ padding: "10px", borderRadius: "8px", border: "1px solid #ccc", flex: 1 }}
            />
            <button
              onClick={handleSearch}
              style={{ padding: "10px 20px", backgroundColor: "#4CAF50", color: "white", borderRadius: "8px", border: "none", cursor: "pointer" }}
            >
              Search
            </button>
          </div>

        {/* Expense List */}
        <ExpenseList expenses={expenses} onEdit={setEditExpense} onDelete={deleteExpense} />
      </div>
    </div>
  );
};

export default Dashboard;
