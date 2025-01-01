import React, { useEffect, useState } from "react";
import Api from "../../axios/api";
import SeatList from "../../components/seat/SeatList";
import { useRecoilValue } from "recoil";
import { userState } from "../../atoms/userAtom";
import LinkContainer from "../../components/common/LinkContainer";
import { toast } from "react-toastify";
import useConfirm from "../../hooks/useConfirm";
import "./SeatPage.scss";

const linkList = [
  {
    route: "/manage/seat-add",
    value: "좌석 추가"
  }
];

const SeatPage = () => {
  const { user } = useRecoilValue(userState);
  const [seatList, setSeatList] = useState([]); // * 데이터를 사용하기 위한 getter, setter

  const confirm = useConfirm();

  const fetchSeatList = async () => {
    try {
      const res = await Api.get("/api/seats/");

      setSeatList(res.data.data);
    } catch (error) {
      toast.error(error.response?.data.msg);
    }
  };

  const handleUpdateSeat = async (data) => {
    try {
      const isConfirmed = await confirm(
        "해당 좌석 정보를 정말로 수정하시겠습니까?"
      );

      if (isConfirmed) {
        const res = await Api.put("/api/seats/seat-update", {
          id: data.id,
          seatNumber: data.seatNumber,
          cost: data.cost
        });

        if (res.status === 200) {
          toast.success(res.data.msg || "수정되었습니다.");
          fetchSeatList();
        }
      }
    } catch (error) {
      const msg =
        error.response?.data?.msg ||
        "서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.";
      toast.error(msg);
    }
  };

  const handleDeleteSeat = async (id) => {
    try {
      const isConfirmed = await confirm("해당 좌석을 정말로 삭제하시겠습니까?");

      if (isConfirmed) {
        const res = await Api.delete(`/api/seats/seat/${id}`);

        if (res.status === 200) {
          toast.success(res.data.msg || "삭제되었습니다.");
          fetchSeatList();
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
    fetchSeatList();
  }, []);

  return (
    <div>
      <section className="seat-section">
        <SeatList
          seatList={seatList}
          onUpdate={handleUpdateSeat}
          onDelete={handleDeleteSeat}
        />
      </section>
      <aside className="seat-aside">
        {user.roles.includes("ROLE_ADMIN") ? (
          <LinkContainer linkList={linkList} />
        ) : null}
      </aside>
    </div>
  );
};

export default SeatPage;
