
public class finalRecTableViewManager
{
    int frIndex;
    String frID;
    String frFname;
    String frLname;
    String frRoll;
    String frClass;
    String frDiv;
    String frSubject;
    String frAttendance;
    String frDate;
    String frReview;

    public finalRecTableViewManager()
    {
        this.frIndex = 0;
        this.frID = "";
        this.frFname = "";
        this.frLname = "";
        this.frRoll = "";
        this.frClass = "";
        this.frDiv = "";
        this.frSubject = "";
        this.frAttendance = "";
        this.frDate = "";
        this.frReview = "";
    }

    public finalRecTableViewManager(int frIndex,String frID,String frFname,String frLname,String frRoll,String frClass,String frDiv, String frSubject,String frAttendance,String frDate,String frReview)
    {
        this.frIndex = frIndex;
        this.frID = frID;
        this.frFname = frFname;
        this.frLname = frLname;
        this.frRoll = frRoll;
        this.frClass = frClass;
        this.frDiv = frDiv;
        this.frSubject = frSubject;
        this.frAttendance = frAttendance;
        this.frDate = frDate;
        this.frReview = frReview;
    }

    public int getFrIndex() {
        return this.frIndex;
    }

    public void setFrIndex(int frIndex) {
        this.frIndex = frIndex;
    }

    public String getFrID() {
        return this.frID;
    }

    public void setFrID(String frID) {
        this.frID = frID;
    }

    public String getFrFname() {
        return this.frFname;
    }

    public void setFrFname(String frFname) {
        this.frFname = frFname;
    }

    public String getFrLname() {
        return this.frLname;
    }

    public void setFrLname(String frLname) {
        this.frLname = frLname;
    }

    public String getFrRoll() {
        return this.frRoll;
    }

    public void setFrRoll(String frRoll) {
        this.frRoll = frRoll;
    }

    public String getFrClass() {
        return this.frClass;
    }

    public void setFrClass(String frClass) {
        this.frClass = frClass;
    }

    public String getFrDiv() {
        return this.frDiv;
    }

    public void setFrDiv(String frDiv) {
        this.frDiv = frDiv;
    }

    public String getFrSubject() {
        return this.frSubject;
    }

    public void setFrSubject(String frSubject) {
        this.frSubject = frSubject;
    }

    public String getFrAttendance() {
        return this.frAttendance;
    }

    public void setFrAttendance(String frAttendance) {
        this.frAttendance = frAttendance;
    }

    public String getFrDate() {
        return this.frDate;
    }

    public void setFrDate(String frDate) {
        this.frDate = frDate;
    }

    public String getFrReview() {
        return this.frReview;
    }

    public void setFrReview(String frReview) {
        this.frReview = frReview;
    }
}