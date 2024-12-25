import React, { useState } from "react";
import "./ServiceInstructionList.scss";

const EditServiceInstructionItem = ({ item, onUpdate, onDelete }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [updateVal, setUpdateVal] = useState(item.content);

  const handleUpdate = () => {
    onUpdate(item.id, updateVal);
    setIsEditing(false);
  };

  return (
    <li className="si-guide__item">
      <div className="si-guide__item-read">
        <div className="flex si-guide__item-main">{item.content}</div>
        <div className="btn-container">
          <button onClick={() => setIsEditing(true)}>수정</button>
          <button onClick={() => onDelete(item.id)}>삭제</button>
        </div>
      </div>
      {isEditing && (
        <div className="si-guide__item-side">
          <div className="input-container input-container__xl">
            <input
              value={updateVal}
              onChange={(e) => setUpdateVal(e.target.value)}
              placeholder={item.content}
              className="input__xl"
            />
          </div>
          <div className="btn-container">
            <button onClick={handleUpdate}>제출</button>
            <button onClick={() => setIsEditing(false)}>취소</button>
          </div>
        </div>
      )}
    </li>
  );
};

export default EditServiceInstructionItem;
