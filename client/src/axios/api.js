import axios from "axios";

const Api = axios.create({
  baseURL: process.env.REACT_APP_API_URL
});

// * 요청 인터셉터
Api.interceptors.request.use(
  async (config) => {
    const token = localStorage.getItem("token"); // Access Token 가져옴
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`; // 헤더에 Access Token 추가
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// * 응답 인터셉터
Api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;

    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true; // 재시도 방지 플래그 설정

      try {
        // Refresh Token 요청
        const refreshResponse = await Api.post(
          "/api/auth/refresh",
          {},
          { withCredentials: true }
        );

        const newAccessToken = refreshResponse.data.token; // 새로운 Access Token

        localStorage.setItem("token", newAccessToken); // 새로운 Access Token 저장
        originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`; // 재요청 시 새로운 Access Token 추가

        return Api(originalRequest); // 원래 요청 다시 전송
      } catch (refreshError) {
        // 리프레시 토큰 요청이 실패하면 로그인 페이지로 이동 -> 리프레시 토큰 만료
        window.location.href = "/auth/signin";
      }
    }

    return Promise.reject(error);
  }
);

export default Api;
