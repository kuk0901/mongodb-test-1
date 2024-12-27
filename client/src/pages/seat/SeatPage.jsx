import React, { useEffect, useState } from "react";
import Api from "../../axios/api";
import SeatList from "../../components/seat/SeatList";
import { useRecoilValue } from "recoil";
import { userState } from "../../atoms/userAtom";
import LinkContainer from "../../components/common/LinkContainer";
import { toast } from "react-toastify";
import "./SeatPage.scss";

const SeatPage = () => {
  const { user } = useRecoilValue(userState);
  const [seatList, setSeatList] = useState([]); // * 데이터를 사용하기 위한 getter, setter

  const fetchSeatList = async () => {
    try {
      const res = await Api.get("/api/seats/");

      setSeatList(res.data.data); // * [] -> 100개
    } catch (error) {
      toast.error(error.response?.data.msg);
    }
  };

  useEffect(() => {
    fetchSeatList();
  }, []);

  const linkList = [
    {
      route: "/manage/seat-add",
      value: "좌석 추가"
    }
  ];

  return (
    <div>
      <section className="seat-section">
        <SeatList seatList={seatList} />
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
