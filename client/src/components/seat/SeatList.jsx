import React from "react";
import SeatItem from "./SeatItem";
import { useRecoilValue } from "recoil";
import { userState } from "../../atoms/userAtom";

const SeatList = ({ seatList }) => {
  const { user } = useRecoilValue(userState);

  return (
    <div className="flex seat-list__container">
      {seatList.map((seat) => (
        <SeatItem
          key={seat.id}
          seat={seat}
          isAdmin={user.roles.includes("ROLE_ADMIN")}
        />
      ))}
    </div>
  );
};

export default SeatList;
