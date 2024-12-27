import React from "react";
import Api from "../../axios/api";

const SeatItem = ({ seat, isAdmin }) => {
  const handleUpdate = async (data) => {
    try {
      await Api.put("/api/seats/seat-update", data);
    } catch (error) {}
  };

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
        <div className="btn-container">
          <button>수정</button>
          <button>삭제</button>
        </div>
      ) : null}
    </li>
  );
};

export default SeatItem;
