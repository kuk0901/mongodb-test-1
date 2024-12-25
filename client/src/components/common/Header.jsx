import React from "react";

const Header = ({ title, description }) => {
  return (
    <header className="header">
      <h3 className="header--title">{title}</h3>
      {description ? <p className="header--description">{description}</p> : null}
    </header>
  );
};

export default Header;
