import React from "react";
import EditServiceInstructionItem from "./EditServiceInstructionItem";

const EditableServiceInstructionList = ({
  serviceInstructionList,
  onUpdate,
  onDelete
}) => {
  return (
    <ol className="si-guide__list">
      {serviceInstructionList.map((el) => (
        <EditServiceInstructionItem
          key={el.id}
          item={el}
          onUpdate={onUpdate}
          onDelete={onDelete}
        />
      ))}
    </ol>
  );
};

export default EditableServiceInstructionList;
