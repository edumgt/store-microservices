/* eslint-disable react/prop-types */
export default function ProfileDetails({ user }) {
  return (
    <>
      <div className="col-md-6">
        <label className="form-label">Fullname</label>
        <input
          type="text"
          className="form-control"
          id="inputFullname"
          defaultValue={user.fullname}
        />
      </div>
      <div className="col-md-6">
        <label className="form-label">Username</label>
        <input
          type="text"
          className="form-control"
          id="inputUsername"
          defaultValue={user.username}
        />
      </div>
      <div className="col-md-6">
        <label className="form-label">Email</label>
        <input
          type="email"
          className="form-control"
          id="inputEmail"
          defaultValue={user.email}
        />
      </div>
      <div className="col-md-6">
        <label className="form-label">Password</label>
        <input
          type="password"
          className="form-control"
          id="inputPassword"
          defaultValue={""}
        />
      </div>
      <div className="col-12 mt-5 d-flex justify-content-center">
        <button className="btn btn-primary">Save Changes</button>
        <button className="btn btn-outline-danger ms-4">Delete Account</button>
      </div>
    </>
  );
}
