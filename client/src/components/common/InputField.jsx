import React from "react";

const InputField = ({
  register,
  name,
  type,
  placeholder,
  error,
  className
}) => {
  return (
    <div className={`input-container input-container__${className}`}>
      <input
        type={type}
        {...register(name, { required: true })}
        placeholder={placeholder}
        className={`input__${className}`}
      />
      {error && <p>{error}</p>}
    </div>
  );
};

export default InputField;
