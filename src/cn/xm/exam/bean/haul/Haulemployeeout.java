package cn.xm.exam.bean.haul;

public class Haulemployeeout {
    private String bigemployeeoutid;

    private String unitid;

    private String employeeid;

    private String bigid;

    private String empoutidcard;

    private String trainstatus;

    private String emptype;

    private String empoutphone;

    private String safehatnum;

    private String thirdscore;

    public String getBigemployeeoutid() {
        return bigemployeeoutid;
    }

    public void setBigemployeeoutid(String bigemployeeoutid) {
        this.bigemployeeoutid = bigemployeeoutid == null ? null : bigemployeeoutid.trim();
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid == null ? null : employeeid.trim();
    }

    public String getBigid() {
        return bigid;
    }

    public void setBigid(String bigid) {
        this.bigid = bigid == null ? null : bigid.trim();
    }

    public String getEmpoutidcard() {
        return empoutidcard;
    }

    public void setEmpoutidcard(String empoutidcard) {
        this.empoutidcard = empoutidcard == null ? null : empoutidcard.trim();
    }

    public String getTrainstatus() {
        return trainstatus;
    }

    public void setTrainstatus(String trainstatus) {
        this.trainstatus = trainstatus == null ? null : trainstatus.trim();
    }

    public String getEmptype() {
        return emptype;
    }

    public void setEmptype(String emptype) {
        this.emptype = emptype == null ? null : emptype.trim();
    }

    public String getEmpoutphone() {
        return empoutphone;
    }

    public void setEmpoutphone(String empoutphone) {
        this.empoutphone = empoutphone == null ? null : empoutphone.trim();
    }

    public String getSafehatnum() {
        return safehatnum;
    }

    public void setSafehatnum(String safehatnum) {
        this.safehatnum = safehatnum == null ? null : safehatnum.trim();
    }

    public String getThirdscore() {
        return thirdscore;
    }

    public void setThirdscore(String thirdscore) {
        this.thirdscore = thirdscore == null ? null : thirdscore.trim();
    }
}