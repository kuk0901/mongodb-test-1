import React from "react";

const InputField = ({
  register,
  name,
  type,
  placeholder,
  error,
  className,
  value,
  onChange
}) => {
  if (register) {
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
  } else {
    return (
      <input
        value={value}
        onChange={onChange}
        type={type}
        placeholder={placeholder}
        className={`input__${className}`}
      />
    );
  }
};

export default InputField;
