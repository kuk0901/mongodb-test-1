import React from "react";

const AdminBtnList = ({ onUpdate, onDelete }) => {
  return (
    <div className="btn-container">
      <button onClick={onUpdate} className="btn tertiary">
        수정
      </button>
      <button onClick={onDelete} className="btn delete">
        삭제
      </button>
    </div>
  );
};

export default AdminBtnList;
