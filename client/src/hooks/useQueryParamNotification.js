import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { toast } from "react-toastify";

// * http:/localhost:3000/auth/signin?id=true&pwd=0000

/*
 * 회원가입이 됨 -> 로그인 페이지 -> 로그인 페이지에서 알림창을 띄워 결과 알림 
 * 
 * 
 */

const useQueryParamNotification = (paramName, message) => {
  const location = useLocation();
  const [hasShown, setHasShown] = useState(false);

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    if (queryParams.get(paramName) === "true" && !hasShown) {
      toast.success(message);
      setHasShown(true);
    }
  }, [location, hasShown, paramName, message]);
};

export default useQueryParamNotification;
