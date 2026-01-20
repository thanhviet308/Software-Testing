package BaiTapApDung.src.orgapp.model;

public class Organization {
    private int orgId;
    private String orgName;
    private String address;
    private String phone;
    private String email;

    public Organization() { }

    public Organization(String orgName, String address, String phone, String email) {
        this.orgName = orgName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getter & Setter
    public int getOrgId() { return orgId; }
    public void setOrgId(int orgId) { this.orgId = orgId; }

    public String getOrgName() { return orgName; }
    public void setOrgName(String orgName) { this.orgName = orgName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
