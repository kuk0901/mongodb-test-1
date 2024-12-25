import React from "react";
import SignupForm from "../../components/auth/SignupForm";
import { Link } from "react-router-dom";
import style from "./auth.module.scss";

const SignupPage = () => {
  return (
    <div className={`border-gray ${style.container} ${style.signup_container}`}>
      <SignupForm />
      <div className="link-container">
        <div>
          <Link to="/auth/signin" className="text-gray">
            로그인
          </Link>
        </div>
      </div>
    </div>
  );
};

export default SignupPage;
