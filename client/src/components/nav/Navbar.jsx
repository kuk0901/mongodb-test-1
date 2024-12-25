import React from "react";
import AdminNav from "./AdminNav";
import UserNav from "./UserNav";
import "./Nav.scss";
import Logo from "../Logo";
import { useRecoilValue } from "recoil";
import { userState } from "../../atoms/userAtom";

const Navbar = () => {
  const { user } = useRecoilValue(userState);

  return (
    <nav className="nav">
      <Logo />
      {user ? renderUserNav(user.roles) : null}
    </nav>
  );
};

// 역할에 따라 네비게이션을 렌더링
const renderUserNav = (roles) => {
  if (roles.includes("ROLE_ADMIN")) {
    return <AdminNav />;
  } else {
    return <UserNav />;
  }
};

export default Navbar;
