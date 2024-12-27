import React from "react";
import useConfirm from "../../hooks/useConfirm";

// TODO: food를 받아 이름과 가격 사용(현재는 전체 메뉴라는 가정)
const FoodItem = ({ food, onDelete, isAdmin }) => {
  const confirm = useConfirm();

  const handleDelete = async () => {
    const isConfirmed = await confirm(
      food.foodName + " 메뉴를 정말로 삭제하시겠습니까?"
    );

    if (isConfirmed) {
      onDelete(food.id);
    }
  };

  return (
    <li className="food-menu__item flex pointer">
      <div className="food-menu__item-name">{food.foodName}</div>
      <div
        className="food-menu__item-price"
        aria-label={`가격: ${food.price.toLocaleString()}`}
      >
        {food.price.toLocaleString()}
      </div>
      {isAdmin && (
        <button onClick={handleDelete} className="btn delete">
          삭제
        </button>
      )}
    </li>
  );
};

export default FoodItem;
