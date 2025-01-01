import { atom } from "recoil";

export const userState = atom({
  key: "userState", // 고유한 ID (다른 atom과 겹치지 않도록)
  default: {
    user: null,
    loading: true
  }
});
