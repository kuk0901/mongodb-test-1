import React, { useCallback, useEffect, useState } from "react";
import InfiniteScroll from "react-infinite-scroll-component";
import Api from "../../axios/api";
import { toast } from "react-toastify";
import "./FoodPage.scss";
import FoodTypeItem from "../../components/food/FoodTypeItem";
import FoodItem from "../../components/food/FoodItem";
import { useRecoilValue } from "recoil";
import { userState } from "../../atoms/userAtom";
import { Link } from "react-router-dom";
import { v4 as uuidv4 } from "uuid";
import MiniLoading from "../../components/loading/MiniLoading";

const FoodMenuPage = () => {
  const { user } = useRecoilValue(userState);

  const [foodList, setFoodList] = useState([]);
  const [foodTypeList, setFoodTypeList] = useState([]);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");

  const handleFoodSearch = async (search = "") => {
    setSearchTerm(search);
    setPage(1);
    setFoodList([]);
    setHasMore(true);
    await fetchFoods(1, search);
  };

  const fetchFoods = useCallback(
    async (pageNum, search = searchTerm) => {
      try {
        const res = await Api.get("/api/menus/", {
          params: { search, page: pageNum, limit: 20 }
        });

        if (pageNum === 1) {
          setFoodList(res.data.data.foodList || []);
          setFoodTypeList(res.data.data.foodTypeList || []);
        } else {
          setFoodList((prevList) => [
            ...prevList,
            ...(res.data.data.foodList || [])
          ]);
        }

        // 전체 데이터와 현재 페이지를 기준으로 hasMore 설정
        const totalItems = res.data.data.totalItems || 0;
        const totalPages = res.data.data.totalPages || 1;

        setHasMore(pageNum < totalPages); // 현재 페이지가 총 페이지 수보다 작으면 hasMore = true
        setPage(pageNum);
      } catch (error) {
        toast.error(
          "메뉴를 가져오는 데 실패했습니다. 잠시 후 다시 시도해 주세요."
        );
      }
    },
    [searchTerm]
  );

  const handleDeleteFood = async (foodId) => {
    try {
      const res = await Api.delete(`/api/menus/${foodId}`);
      toast.success(res.data.msg);
      await handleFoodSearch(searchTerm);
    } catch (error) {
      const msg =
        error.response.data.data ||
        "서버 오류가 발생했습니다. 잠시후 다시 시도해 주세요.";
      toast.error(msg);
    }
  };

  useEffect(() => {
    fetchFoods(1);
  }, [fetchFoods]);

  const foodTypeItems = foodTypeList?.map((foodType) => (
    <FoodTypeItem
      key={uuidv4()}
      foodType={foodType}
      onClick={() => handleFoodSearch(foodType.type)}
    />
  ));

  return (
    <div className="food-menu">
      <aside className="flex food-menu__sidebar">
        <ul className="flex food-menu__type-list">
          <li className="food-menu__type-item">
            <button
              onClick={() => handleFoodSearch()}
              className="btn primary food-menu__type-btn"
            >
              전체
            </button>
          </li>
          {foodTypeItems}
        </ul>

        {user.roles.includes("ROLE_ADMIN") && (
          <div className="flex link-container">
            <Link to="/manage/food-add" className="btn secondary">
              메뉴 추가
            </Link>
          </div>
        )}
      </aside>
      <main className="food-menu__main">
        {/* 무한 스크롤 사용 형태 //TODO: 추후 수정 필요 */}
        <InfiniteScroll
          dataLength={foodList.length}
          next={() => fetchFoods(page + 1)}
          hasMore={hasMore}
          loader={<MiniLoading />} // TODO: scss 수정 필요 -> mini loading component
          endMessage={<p className="get-all-menus">모든 메뉴를 불러왔습니다.</p>} // TODO: scss 수정 필요 -> css
        >
          <ul className="flex food-menu__list">
            {foodList && foodList.length > 0 ? (
              foodList.map((food) => (
                <FoodItem
                  key={food.id}
                  food={food}
                  onDelete={handleDeleteFood}
                  isAdmin={user.roles.includes("ROLE_ADMIN")}
                />
              ))
            ) : (
              <li className="none-menus">메뉴가 없습니다.</li>
            )}
          </ul>
        </InfiniteScroll>
      </main>
    </div>
  );
};

export default FoodMenuPage;
