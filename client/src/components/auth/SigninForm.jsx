import React from "react";
import { useForm } from "react-hook-form";
// import { useUser } from "../../context/UserContext"; // UserContext import
import { useNavigate } from "react-router-dom";
import Api from "../../axios/api";
import { toast } from "react-toastify";
import InputField from "../common/InputField";
import SubmitButton from "../common/SubmitButton";
import { useSetRecoilState } from "recoil";
import { userState } from "../../atoms/userAtom";

const SigninForm = () => {
  const setUserInfo = useSetRecoilState(userState);
  // const { setUser } = useUser(); // 사용자 정보 설정 함수 가져오기
  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm(); // React Hook Form 훅 사용

  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      const res = await Api.post(
        "/api/auth/signin",
        {
          userId: data.userId,
          pwd: data.pwd
        },
        { withCredentials: true }
      );

      // 서버에서 반환된 데이터에서 액세스 토큰과 유저 데이터 추출
      const accessToken = res.data.token; // 액세스 토큰
      const userInfoData = res.data.data;

      localStorage.setItem("token", accessToken); // JWT 저장
      setUserInfo({
        user: { userName: userInfoData.userName, roles: userInfoData.roles },
        loading: false
      });

      navigate("/home");
    } catch (error) {
      toast.error(
        error.response?.data.msg ||
          "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."
      ); // 에러 메시지 표시
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <InputField
        register={register}
        name="userId"
        type="text"
        placeholder="ID"
        error={errors.userId && "ID is required"}
        className="s input__auth"
      />
      <InputField
        register={register}
        name="pwd"
        type="password"
        placeholder="Password"
        error={errors.pwd && "Password is required"}
        className="s input__auth"
      />
      <SubmitButton text="로그인" />
    </form>
  );
};

export default SigninForm;
