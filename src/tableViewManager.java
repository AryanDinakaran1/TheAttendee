
public class tableViewManager
{
    int id;
    String fname;
    String lname;
    String roll;
    String theClass;
    String div;
    String email;
    String phoneNum;
    String date;

    public tableViewManager()
    {
        this.id = 0;
        this.fname = "";
        this.lname = "";
        this.roll = "";
        this.theClass = "";
        this.div = "";
        this.email = "";
        this.phoneNum = "";
        this.date = "";
    }

    public tableViewManager(int id,String fname,String lname,String roll,String theClass,String div,String email,String phoneNum,String date)
    {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.roll = roll;
        this.theClass = theClass;
        this.div = div;
        this.email = email;
        this.phoneNum = phoneNum;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getTheClass() {
        return theClass;
    }

    public void setTheClass(String theClass) {
        this.theClass = theClass;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}