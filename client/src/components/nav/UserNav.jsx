import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaUserCircle } from "react-icons/fa";
import Api from "../../axios/api";
import { useRecoilState } from "recoil";
import { userState } from "../../atoms/userAtom";

const UserNav = () => {
  const [{ user }, setUserInfo] = useRecoilState(userState);
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleLogout = async () => {
    try {
      await Api.post("/api/auth/signout", {}, { withCredentials: true });

      // 사용자 정보와 토큰 제거
      setUserInfo({ user: null, loading: false });
      localStorage.removeItem("token"); // 로컬 스토리지에서 토큰 제거

      // 로그인 페이지로 리다이렉트 (쿼리 파라미터 추가)
      navigate("/auth/signin?signout=true");
    } catch (error) {
      console.error("로그아웃 실패:", error);
    }
  };

  return (
    <div className="flex user_nav">
      <div className="flex menu">
        <div className="menu-item btn secondary">
          <Link to="/seat-menu">자리</Link>
        </div>
        <div className="menu-item btn secondary">
          <Link to="#">요금</Link>
        </div>
        <div className="menu-item btn secondary">
          <Link to="/food-menu">음식(메뉴)</Link>
        </div>
      </div>

      <button
        onClick={() => setIsDropdownOpen(!isDropdownOpen)}
        className="pointer user__icon"
      >
        <FaUserCircle size={30} />
      </button>

      {/* 드롭다운 메뉴 */}
      {isDropdownOpen && (
        <ul className="flex dropdown_menu">
          <li className="user_info">
            <Link to="#">{user.userName}님</Link>
          </li>
          <li className="btn_signout">
            <button onClick={handleLogout}>로그아웃</button>
          </li>
        </ul>
      )}
    </div>
  );
};

export default UserNav;
