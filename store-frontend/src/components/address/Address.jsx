/* eslint-disable react/prop-types */
export default function Address({ address }) {
  return (
    <>
      <div className="col-md-4">
        <label className="form-label">Address Line</label>
        <input
          type="text"
          className="form-control"
          //   id="inputAddressLine"
          defaultValue={address.addressLine}
          disabled
        />
      </div>
      <div className="col-md-2">
        <label className="form-label">Country</label>
        <input
          type="text"
          className="form-control"
          //   id="inputCountry"
          defaultValue={address.country}
          disabled
        />
      </div>
      <div className="col-md-2">
        <label className="form-label">State</label>
        <input
          type="text"
          className="form-control"
          //   id="inputState"
          defaultValue={address.state}
          disabled
        />
      </div>
      <div className="col-md-2">
        <label className="form-label">City</label>
        <input
          type="text"
          className="form-control"
          //   id="inputCity"
          defaultValue={address.city}
          disabled
        />
      </div>
      <div className="col-md-2">
        <label className="form-label">Pincode</label>
        <input
          type="number"
          className="form-control"
          id="inputPincode"
          min={100000}
          max={999999}
          defaultValue={address.pincode}
          disabled
        />
      </div>
    </>
  );
}
