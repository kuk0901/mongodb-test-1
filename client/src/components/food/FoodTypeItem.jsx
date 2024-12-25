import React from "react";

// TODO: <button onClick={handleAllList}>전체</button> 구조 확인해서 생성, api를 받아서 서버측에 전달해 검색
// * ->  호출할 엔드포인트 다름 -> 실제로 사용될 서비스 로직 및 레포지토리 함수 다름
const FoodTypeItem = ({ foodType, onClick }) => {
  return (
    <li className="food-menu__type-item">
      <button onClick={onClick} className="btn primary food-menu__type-btn">
        {foodType.type}
      </button>
    </li>
  );
};



export default FoodTypeItem;
