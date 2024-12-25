import React from "react";

const ServiceInstructionList = ({ serviceInstructionList }) => {
  return (
    <div className="si-guide">
      <ol className="si-guide__list">
        {serviceInstructionList.map((el) => (
          <li key={el.id} className="si-guide__item">
            {el.content}
          </li>
        ))}
      </ol>
    </div>
  );
};

export default ServiceInstructionList;
