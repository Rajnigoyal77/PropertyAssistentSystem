package browsecustomer;

public class CustomerBean {

    String mobile;
    String name;
    String city;
    String adresss;
    String email;
    String dae;

    public CustomerBean(String mobile, String name, String city,
                        String adresss, String email, String dae)
    {
        this.mobile = mobile;
        this.name = name;
        this.city = city;
        this.adresss = adresss;
        this.email = email;
        this.dae = dae;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdresss() {
        return adresss;
    }

    public void setAdresss(String adresss) {
        this.adresss = adresss;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDae() {
        return dae;
    }

    public void setDae(String dae) {
        this.dae = dae;
    }
}