import React from "react";
import { useNavigate } from "react-router-dom";
import Api from "../../axios/api";
import { toast } from "react-toastify";
import Form from "../common/Form";

const SignupForm = () => {
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      const res = await Api.post("/api/auth/signup", {
        userId: data.userId,
        userName: data.userName,
        pwd: data.pwd
      });

      if (res.status === 200) {
        navigate("/auth/signin?registered=true");
      }
    } catch (error) {
      toast.error(
        error.response?.data.msg ||
          "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."
      );
    }
  };

  const fields = [
    {
      name: "userName",
      type: "text",
      placeholder: "Name",
      errorMessage: "Name is required",
      field: "input",
      className: "s input__auth"
    },
    {
      name: "userId",
      type: "text",
      placeholder: "ID",
      errorMessage: "ID is required",
      field: "input",
      className: "s input__auth"
    },
    {
      name: "pwd",
      type: "password",
      placeholder: "Password",
      errorMessage: "Password is required",
      field: "input",
      className: "s input__auth"
    }
  ];

  return <Form onSubmit={onSubmit} fields={fields} btnText="회원가입" />;
};

export default SignupForm;
