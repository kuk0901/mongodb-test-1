import React from "react";
import { toast } from "react-toastify";
import Api from "../../axios/api";
import Form from "../common/Form";

const SeatAddForm = () => {
  const onSubmit = async (data, reset) => {
    try {
      const res = await Api.post("/api/seats/seat-add", data);

      if (res.status === 200) {
        toast.success(res.data.msg);
        reset();
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
      name: "seatNumber",
      type: "number",
      placeholder: "좌석 번호",
      errorMessage: "좌석 번호는 필수 입력란입니다.",
      field: "input"
    },
    {
      name: "cost",
      type: "number",
      placeholder: "가격",
      errorMessage: "가격은 필수 입력란입니다.",
      field: "input"
    }
  ];

  return <Form onSubmit={onSubmit} fields={fields} btnText="추가" />;
};

export default SeatAddForm;
