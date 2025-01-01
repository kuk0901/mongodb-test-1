import React, { useState } from "react";
import InputField from "../common/InputField";
import { toast } from "react-toastify";

const SeatItem = ({ seat, isAdmin, onUpdate, onDelete }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [updateVal, setUpdateVal] = useState({
    id: seat.id,
    seatNumber: seat.seatNumber,
    cost: seat.cost
  });
  const [isChanged, setIsChanged] = useState(false);

  // 값 변경 확인
  const handleUpdate = () => {
    if (!isChanged) {
      toast.info("변경 사항이 없습니다.");
      return;
    }
    onUpdate(updateVal);
    setIsEditing(false);
  };

  // * 고차함수
  const handleChange = (field) => (e) => {
    const value = e.target.value;
    // * (prev) => ({ ...prev, [field]: value }
    setUpdateVal((prev) => ({ ...prev, [field]: value }));

    // isChanged 상태 업데이트
    const newValue = Number(value);

    const changed =
      (field === "seatNumber" && seat.seatNumber !== newValue) ||
      (field === "cost" && seat.cost !== newValue);

    setIsChanged(changed); // 변경 여부 업데이트
  };

  const fields = [
    {
      id: "seatNumber_",
      name: "seatNumber",
      onChange: handleChange("seatNumber"),
      type: "number",
      placeholder: "좌석 번호를 입력하세요.",
      className: "s admin-input__item"
    },
    {
      id: "cost_",
      name: "cost",
      onChange: handleChange("cost"),
      type: "number",
      placeholder: "좌석 가격을 입력하세요.",
      className: "s admin-input__item"
    }
  ];

  return (
    <li>
      {/* * 여기는 좌석에 대한 정보 */}
      <div className="seat-item__container">
        <div className="seat-item__seatNumber">
          좌석번호: <span className="bold">{seat.seatNumber}</span>
        </div>
        <div className="seat-item__cost">
          가격: {seat.cost.toLocaleString()}
        </div>
      </div>

      {isAdmin ? (
        <div>
          <div className="btn-container">
            <button onClick={() => setIsEditing(true)} className="btn tertiary">
              수정
            </button>
            <button onClick={() => onDelete(seat.id)} className="btn delete">
              삭제
            </button>
          </div>

          {isEditing && (
            <div className="si-guide__item-side">
              <div className="input-container input-container__s flex admin-input__list">
                {fields.map((field) => (
                  <InputField
                    key={field.id}
                    onChange={field.onChange}
                    type={field.type}
                    placeholder={field.placeholder}
                    className={field.className}
                    value={String(updateVal[field.name])}
                    name={field.name}
                  />
                ))}
              </div>
              <div className="btn-container">
                <button onClick={handleUpdate} disabled={!isChanged}>
                  제출
                </button>
                <button onClick={() => setIsEditing(false)}>취소</button>
              </div>
            </div>
          )}
        </div>
      ) : null}
    </li>
  );
};

export default SeatItem;
