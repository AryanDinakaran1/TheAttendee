
public class attendance
{
    int idAtt;
    String fnameAtt;
    String lnameAtt;
    String rollAtt;
    String theClassAtt;
    String divAtt;
    String subject;
    String attendance;
    String review;

    public attendance()
    {
        this.idAtt = 0;
        this.fnameAtt = "";
        this.lnameAtt = "";
        this.rollAtt = "";
        this.theClassAtt = "";
        this.divAtt = "";
        this.subject = "";
        this.attendance = "";
        this.review = "";
    }

    public attendance(int idAtt,String fnameAtt,String lnameAtt,String rollAtt,String theClassAtt,String divAtt,String subject,String attendance,String review)
    {
        this.idAtt = idAtt;
        this.fnameAtt = fnameAtt;
        this.lnameAtt = lnameAtt;
        this.rollAtt = rollAtt;
        this.theClassAtt = theClassAtt;
        this.divAtt = divAtt;
        this.subject = subject;
        this.attendance = attendance;
        this.review = review;
    }

    public int getIdAtt() {
        return this.idAtt;
    }

    public void setIdAtt(int idAtt) {
        this.idAtt = idAtt;
    }

    public String getFnameAtt() {
        return this.fnameAtt;
    }

    public void setFnameAtt(String fnameAtt) {
        this.fnameAtt = fnameAtt;
    }

    public String getLnameAtt() {
        return this.lnameAtt;
    }

    public void setLnameAtt(String lnameAtt) {
        this.lnameAtt = lnameAtt;
    }

    public String getRollAtt() {
        return this.rollAtt;
    }

    public void setRollAtt(String rollAtt) {
        this.rollAtt = rollAtt;
    }

    public String getTheClassAtt() {
        return this.theClassAtt;
    }

    public void setTheClassAtt(String theClassAtt) {
        this.theClassAtt = theClassAtt;
    }

    public String getDivAtt() {
        return this.divAtt;
    }

    public void setDivAtt(String divAtt) {
        this.divAtt = divAtt;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttendance() {
        return this.attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}