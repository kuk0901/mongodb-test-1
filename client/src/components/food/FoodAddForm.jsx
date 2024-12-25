import React from "react";
import Form from "../common/Form";
import Api from "../../axios/api";
import { toast } from "react-toastify";

const FoodAddForm = () => {
  const onSubmit = async (data, reset) => {
    try {
      const res = await Api.post("/api/menus/food-add", data);

      if (res.status === 200) {
        toast.success(res.data.data);
        reset();
      }
    } catch (error) {
      console.log(error.response);
      toast.error(
        error.response?.data.data ||
          "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."
      );
    }
  };

  const fields = [
    {
      name: "foodName",
      type: "text",
      placeholder: "음식 명",
      errorMessage: "음식 명은 필수 입력란입니다.",
      field: "input"
    },
    {
      name: "price",
      type: "number",
      placeholder: "가격",
      errorMessage: "가격은 필수 입력란입니다.",
      field: "input"
    },
    {
      name: "type",
      type: "text",
      placeholder: "종류",
      errorMessage: "음식 종류는 필수 입력란입니다.",
      field: "input"
    }
  ];

  return <Form onSubmit={onSubmit} fields={fields} btnText="추가" />;
};

export default FoodAddForm;
