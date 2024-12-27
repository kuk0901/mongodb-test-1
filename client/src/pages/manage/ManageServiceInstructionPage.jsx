import React, { useEffect, useState } from "react";
import useQueryParamNotification from "../../hooks/useQueryParamNotification";
import CreateServiceInstructionForm from "../../components/cs/si/CreateServiceInstructionForm";
import Api from "../../axios/api";
import { toast } from "react-toastify";
import useConfirm from "../../hooks/useConfirm";
import EditableServiceInstructionList from "../../components/cs/si/EditableServiceInstructionList";
import "./ManageServiceInstructionPage.scss";

const ManageServiceInstructionPage = () => {
  useQueryParamNotification("registered", "안내사항이 추가되었습니다.");

  const [serviceInstructionList, setServiceInstructionList] = useState([]);
  const confirm = useConfirm();

  const fetchServiceInstructions = async () => {
    try {
      const res = await Api.get("/api/cs/si/guide", {
        params: { type: "si" }
      });
      setServiceInstructionList(res.data.data || []);
    } catch (error) {
      const msg =
        error.response?.data?.msg ||
        "서버 오류로 인해 안내사항을 가져오지 못했습니다. 잠시 후 다시 시도해 주세요.";
      toast.error(msg);
    }
  };

  const handleUpdateSi = async (id, content) => {
    try {
      const isConfirmed = await confirm(
        "해당 안내사항을 정말로 수정하시겠습니까?"
      );

      if (isConfirmed) {
        const res = await Api.patch(`/api/cs/si/guide-update`, {
          id: id,
          content: content
        });

        if (res.status === 200) {
          toast.success(res.data.msg || "수정되었습니다.");
          fetchServiceInstructions(); // 목록 새로고침
        }
      }
    } catch (error) {
      const msg =
        error.response?.data?.msg ||
        "서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.";
      toast.error(msg);
    }
  };

  const handleDeleteSi = async (id) => {
    try {
      const isConfirmed = await confirm(
        "해당 안내사항을 정말로 삭제하시겠습니까?"
      );

      if (isConfirmed) {
        const res = await Api.delete(`/api/cs/si/guide/${id}`);

        if (res.status === 200) {
          toast.success(res.data.msg || "삭제되었습니다.");
          fetchServiceInstructions(); // 목록 새로고침
        }
      }
    } catch (error) {
      const msg =
        error.response?.data?.msg ||
        "서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.";
      toast.error(msg);
    }
  };

  useEffect(() => {
    fetchServiceInstructions();
  }, []);

  return (
    <div>
      <section className="add-si">
        <CreateServiceInstructionForm onSuccess={fetchServiceInstructions} />
      </section>
      <section className="edit-si">
        <EditableServiceInstructionList
          serviceInstructionList={serviceInstructionList}
          onUpdate={handleUpdateSi}
          onDelete={handleDeleteSi}
        />
      </section>
    </div>
  );
};

export default ManageServiceInstructionPage;
