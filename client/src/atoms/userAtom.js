import { atom } from "recoil";

// TODO: context 사용 영역 recoil로 변형
// * Nav, Form, App
export const userState = atom({
  key: "userState", // 고유한 ID (다른 atom과 겹치지 않도록)
  default: {
    user: null,
    loading: true
  }
});
