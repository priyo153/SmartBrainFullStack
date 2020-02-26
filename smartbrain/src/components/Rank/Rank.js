import React from "react";

const Rank = ({ name, entries }) => {
  return (
    <div>
      <div className="purple f3 fw8">
        {`${name}, your current number of entries is...`}
      </div>
      <div className="purple f2">{entries}</div>
    </div>
  );
};

export default Rank;
