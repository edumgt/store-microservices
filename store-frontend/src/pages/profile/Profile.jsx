import AddressList from "../../components/address/AddressList";
import ProfileDetails from "../../components/profiledetails/ProfileDetails";

export default function Profile() {
  const loggedIn = "true" === localStorage.getItem("loggedIn");
  if (!loggedIn) {
    window.location.href = "/home";
  }

  const user = {
    userId: "c69f953b-4cb7-42b7-b67e-cc2329b1782f",
    fullname: "Praveen Ukkoji",
    username: "praveenukkoji",
    email: "praveenukkoji@gmail.com",
    roleType: "ADMIN",
    addressList: [
      {
        addressId: "37r10e51-4432-4b72-a3eb-06e4161d0c02",
        addressLine: "updated1",
        country: "updated",
        state: "updated1",
        city: "updated",
        pincode: 123456,
        isDefault: false,
      },
      {
        addressId: "37e10e51-4432-4b72-a3eb-06e4161d0c02",
        addressLine: "updated1",
        country: "updated",
        state: "updated1",
        city: "updated",
        pincode: 123456,
        isDefault: false,
      },
    ],
  };
  return (
    <>
      {loggedIn ? (
        <div className="row g-3 mt-4" style={{ margin: "5%" }}>
          <p className="m-0" style={{ fontSize: 50, fontWeight: "bold" }}>
            Hello, {user.fullname}
          </p>
          <ProfileDetails user={user} />
          <div className="col-md-12 mt-5 d-flex justify-content-between align-items-center p-0">
            <div>
              <h4 className="m-0">Address List : </h4>
            </div>
            <button className="btn btn-primary">Add Address</button>
          </div>
          {user.addressList.length > 0 ? (
            <AddressList key={user.userId} addressList={user.addressList} />
          ) : (
            <p className="mt-4" style={{ fontSize: 20 }}>
              No address added yet
            </p>
          )}
        </div>
      ) : null}
    </>
  );
}
