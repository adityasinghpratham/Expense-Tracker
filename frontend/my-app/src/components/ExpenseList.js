// src/components/ExpenseList.js
import React from "react";

const ExpenseList = ({ expenses, onEdit, onDelete }) => {
  return (
    <table className="table table-striped">
      <thead>
        <tr>
          <th>Title</th>
          <th>Amount</th>
          <th>Category</th>
          <th>Date</th>
          <th>Description</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {expenses.map(exp => (
          <tr key={exp.id}>
            <td>{exp.title}</td>
            <td>{exp.amount}</td>
            <td>{exp.category}</td>
            <td>{exp.date}</td>
            <td>{exp.description}</td>
            <td>
              <button className="btn btn-sm btn-warning me-2" onClick={() => onEdit(exp)}>
                Edit
              </button>
              <button className="btn btn-sm btn-danger" onClick={() => onDelete(exp.id)}>
                Delete
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default ExpenseList;
