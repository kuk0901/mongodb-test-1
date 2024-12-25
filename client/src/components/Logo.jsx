import React from "react";
import { IoLogoGameControllerB } from "react-icons/io";
import { Link } from "react-router-dom";

const Logo = () => {
  return (
    <Link to="/home" className="logo">
      <IoLogoGameControllerB className="icon" />
    </Link>
  );
};

export default Logo;
