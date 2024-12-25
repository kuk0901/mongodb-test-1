import React, { useEffect, useState } from "react";
import "./HomePage.scss";
import Header from "../../components/common/Header";
import ServiceInstructionList from "../../components/cs/si/ServiceInstructionList";
import LinkContainer from "../../components/common/LinkContainer";
import { useRecoilValue } from "recoil";
import { userState } from "../../atoms/userAtom";

import { toast } from "react-toastify";
import Api from "../../axios/api";

const HomePage = () => {
  const { user } = useRecoilValue(userState);
  const [serviceInstructionList, setServiceInstructionList] = useState([]);

  const linkList = [
    {
      route: "/manage/service-instructions",
      value: "이용 안내사항 관리"
    }
  ];

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

  useEffect(() => {
    fetchServiceInstructions();
  }, []);

  return (
    <div className="home-container">
      <main className="flex main-container">
        <section className="home-main__section"></section>
        <aside className="home-main__aside">
          <Header title="이용 안내사항" />
          {user.roles.includes("ROLE_ADMIN") ? (
            <LinkContainer linkList={linkList} />
          ) : null}
          <ServiceInstructionList
            serviceInstructionList={serviceInstructionList}
          />
        </aside>
      </main>
    </div>
  );
};

export default HomePage;
