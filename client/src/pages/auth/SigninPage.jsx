import React, { useEffect, useState } from "react";
import SigninForm from "../../components/auth/SigninForm";
import { Link, useLocation } from "react-router-dom";
import { toast } from "react-toastify";
import style from "./auth.module.scss";

const SigninPage = () => {
  const location = useLocation();
  const [hasShownSignoutMessage, setHasShownSignoutMessage] = useState(false);
  const [hasShownRegisteredMessage, setHasShownRegisteredMessage] =
    useState(false);

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);

    if (
      queryParams.get("registered") === "true" &&
      !hasShownRegisteredMessage
    ) {
      toast.success("회원가입이 완료되었습니다!"); // 회원가입
      setHasShownRegisteredMessage(true); // 메시지 표시 후 상태 업데이트
    }

    if (queryParams.get("signout") === "true" && !hasShownSignoutMessage) {
      toast.success("로그아웃 되었습니다."); // 로그아웃
      setHasShownSignoutMessage(true); // 메시지 표시 후 상태 업데이트
    }
  }, [location, hasShownSignoutMessage, hasShownRegisteredMessage]);

  return (
    <div className={`border-gray ${style.container} ${style.signin_container}`}>
      <SigninForm />
      <div className="link-container">
        <div>
          <Link to="/auth/signup" className="text-gray">회원가입</Link>
        </div>
      </div>
    </div>
  );
};

export default SigninPage;
