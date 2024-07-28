/* eslint-disable react/prop-types */
import React from "react";
import Address from "./Address";

export default function AddressList({ addressList }) {
  return (
    <>
      {addressList.map((address) => {
        return (
          <React.Fragment key={address.addressId}>
            <Address address={address} />
            <div className="d-flex justify-content-center mt-4 p-0">
              <button className="btn btn-primary">Edit Address</button>
              <button className="btn btn-outline-danger ms-4">
                Delete Address
              </button>
            </div>
          </React.Fragment>
        );
      })}
    </>
  );
}
