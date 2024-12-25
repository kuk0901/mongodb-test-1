import React from "react";
import { useNavigate } from "react-router-dom";
import Api from "../../../axios/api";
import { toast } from "react-toastify";
import Form from "../../common/Form";

const CreateServiceInstructionForm = ({ onSuccess }) => {
  const navigate = useNavigate();

  const onSubmit = async (data, reset) => {
    try {
      const res = await Api.post("/api/cs/si/guide-add", data);

      if (res.status === 200) {
        navigate("/manage/service-instructions?registered=true");
        reset();
        onSuccess();
      }
    } catch (error) {
      const msg =
        error.response.data.msg ||
        "서버 오류로 인해 안내사항을 추가하지 못했습니다. 잠시 후 다시 시도해 주세요.";
      toast.error(msg);
    }
  };

  const fields = [
    {
      name: "type",
      type: "text",
      placeholder: "분류",
      errorMessage: "필수 입력란입니다.",
      field: "input",
      className: "s"
    },
    {
      name: "title",
      type: "text",
      placeholder: "제목",
      errorMessage: "필수 입력란입니다.",
      field: "input",
      className: "l"
    },
    {
      name: "content",
      type: "text",
      placeholder: "내용",
      errorMessage: "필수 입력란입니다.",
      className: "xl",
      field: "input"
    }
  ];

  return (
    <div>
      <Form onSubmit={onSubmit} fields={fields} btnText="안내사항 추가" />
    </div>
  );
};

export default CreateServiceInstructionForm;
