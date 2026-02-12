// src/components/ExpenseForm.js
import React, { useState, useEffect } from "react";

const ExpenseForm = ({ onSubmit, expenseToEdit }) => {
  const [expense, setExpense] = useState({
    title: "",
    amount: "",
    category: "",
    date: "",
    description: ""
  });

  useEffect(() => {
    if (expenseToEdit) setExpense(expenseToEdit);
  }, [expenseToEdit]);

  const handleChange = e => {
    setExpense({ ...expense, [e.target.name]: e.target.value });
  };

  const handleSubmit = e => {
    e.preventDefault();
    onSubmit(expense);
    setExpense({ title: "", amount: "", category: "", date: "", description: "" });
  };

  return (
    <form onSubmit={handleSubmit} className="mb-4">
      <div className="row">
        <div className="col-md-3">
          <input
            type="text"
            name="title"
            placeholder="Title"
            className="form-control"
            value={expense.title}
            onChange={handleChange}
            required
          />
        </div>
        <div className="col-md-2">
          <input
            type="number"
            name="amount"
            placeholder="Amount"
            className="form-control"
            value={expense.amount}
            onChange={handleChange}
            required
          />
        </div>
        <div className="col-md-2">
          <input
            type="text"
            name="category"
            placeholder="Category"
            className="form-control"
            value={expense.category}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-2">
          <input
            type="date"
            name="date"
            className="form-control"
            value={expense.date}
            onChange={handleChange}
            required
          />
        </div>
        <div className="col-md-2">
          <input
            type="text"
            name="description"
            placeholder="Description"
            className="form-control"
            value={expense.description}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-1">
          <button type="submit" className="btn btn-primary w-100">
            {expenseToEdit ? "Update" : "Add"}
          </button>
        </div>
      </div>
    </form>
  );
};

export default ExpenseForm;
