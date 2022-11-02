package abl.frd.qremit.model;

import javax.persistence.*;

@Entity
@Table(name="nafex_data_table")
public class NafexModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    //@GeneratedValue(strategy = SEQUENCE, generator = "riaSeqGen")
    //@SequenceGenerator(name = "riaSeqGen", sequenceName = "ria_seq", initialValue = 1)
    private int id;
    @Column(name = "exCode")
    private String exCode;
    @Column(name = "tranNo")
    private String tranNo;
    @Column(name = "currency")
    private String currency;
    @Column(name = "amount")
    private double amount;
    @Column(name = "enteredDate")
    private String enteredDate;
    @Column(name = "remitter")
    private String remitter;
    @Column(name = "beneficiary")
    private String beneficiary;
    @Column(name = "beneficiaryAccount")
    private String beneficiaryAccount;
    @Column(name = "beneficiaryMobile")
    private String beneficiaryMobile;
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "bankCode")
    private String bankCode;
    @Column(name = "branchName")
    private String branchName;
    @Column(name = "branchCode")
    private String branchCode;
    @Column(name = "draweeBranchName")
    private String draweeBranchName;
    @Column(name = "draweeBranchCode")
    private String draweeBranchCode;
    @Column(name = "purposeOfRemittance")
    private String purposeOfRemittance;
    @Column(name = "sourceOfIncome")
    private String sourceOfIncome;
    @Column(name = "remitterMobile")
    private String remitterMobile;


    public NafexModel(){

    }

    @Column(name = "checkt24")
    private String checkT24;
    @Column(name = "checkCoc")
    private String checkCoc;
    @Column(name = "checkAccPayee")
    private String checkAccPayee;
    @Column(name = "checkBeftn")
    private String checkBeftn;


    @Column(name = "fileUploadedDateTime")
    private String fileUploadedDateTime;
    @Column(name = "fileUploadedUserIp")
    private String fileUploadedUserIp;
    @Column(name = "checkProcessed")
    private String checkProcessed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(String enteredDate) {
        this.enteredDate = enteredDate;
    }

    public String getRemitter() {
        return remitter;
    }

    public void setRemitter(String remitter) {
        this.remitter = remitter;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getBeneficiaryMobile() {
        return beneficiaryMobile;
    }

    public void setBeneficiaryMobile(String beneficiaryMobile) {
        this.beneficiaryMobile = beneficiaryMobile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDraweeBranchName() {
        return draweeBranchName;
    }

    public void setDraweeBranchName(String draweeBranchName) {
        this.draweeBranchName = draweeBranchName;
    }

    public String getDraweeBranchCode() {
        return draweeBranchCode;
    }

    public void setDraweeBranchCode(String draweeBranchCode) {
        this.draweeBranchCode = draweeBranchCode;
    }

    public String getPurposeOfRemittance() {
        return purposeOfRemittance;
    }

    public void setPurposeOfRemittance(String purposeOfRemittance) {
        this.purposeOfRemittance = purposeOfRemittance;
    }

    public String getSourceOfIncome() {
        return sourceOfIncome;
    }

    public void setSourceOfIncome(String sourceOfIncome) {
        this.sourceOfIncome = sourceOfIncome;
    }

    public String getRemitterMobile() {
        return remitterMobile;
    }

    public void setRemitterMobile(String remitterMobile) {
        this.remitterMobile = remitterMobile;
    }

    public String getCheckT24() {
        return checkT24;
    }

    public void setCheckT24(String checkT24) {
        this.checkT24 = checkT24;
    }

    public String getCheckCoc() {
        return checkCoc;
    }

    public void setCheckCoc(String checkCoc) {
        this.checkCoc = checkCoc;
    }

    public String getCheckAccPayee() {
        return checkAccPayee;
    }

    public void setCheckAccPayee(String checkAccPayee) {
        this.checkAccPayee = checkAccPayee;
    }

    public String getCheckBeftn() {
        return checkBeftn;
    }

    public void setCheckBeftn(String checkBeftn) {
        this.checkBeftn = checkBeftn;
    }

    public String getFileUploadedDateTime() {
        return fileUploadedDateTime;
    }

    public void setFileUploadedDateTime(String fileUploadedDateTime) {
        this.fileUploadedDateTime = fileUploadedDateTime;
    }

    public String getFileUploadedUserIp() {
        return fileUploadedUserIp;
    }

    public void setFileUploadedUserIp(String fileUploadedUserIp) {
        this.fileUploadedUserIp = fileUploadedUserIp;
    }

    public String getCheckProcessed() {
        return checkProcessed;
    }

    public void setCheckProcessed(String checkProcessed) {
        this.checkProcessed = checkProcessed;
    }

    @Override
    public String toString() {
        return "NafexModel{" +
                "id=" + id +
                ", exCode='" + exCode + '\'' +
                ", tranNo='" + tranNo + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", enteredDate='" + enteredDate + '\'' +
                ", remitter='" + remitter + '\'' +
                ", beneficiary='" + beneficiary + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", beneficiaryMobile='" + beneficiaryMobile + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", draweeBranchName='" + draweeBranchName + '\'' +
                ", draweeBranchCode='" + draweeBranchCode + '\'' +
                ", purposeOfRemittance='" + purposeOfRemittance + '\'' +
                ", sourceOfIncome='" + sourceOfIncome + '\'' +
                ", remitterMobile='" + remitterMobile + '\'' +
                ", checkT24='" + checkT24 + '\'' +
                ", checkCoc='" + checkCoc + '\'' +
                ", checkAccPayee='" + checkAccPayee + '\'' +
                ", checkBeftn='" + checkBeftn + '\'' +
                ", fileUploadedDateTime='" + fileUploadedDateTime + '\'' +
                ", fileUploadedUserIp='" + fileUploadedUserIp + '\'' +
                ", checkProcessed='" + checkProcessed + '\'' +
                '}';
    }

    public NafexModel(String exCode, String tranNo, String currency, double amount, String enteredDate, String remitter, String beneficiary, String beneficiaryAccount, String beneficiaryMobile, String bankName, String bankCode, String branchName, String branchCode, String draweeBranchName, String draweeBranchCode, String purposeOfRemittance, String sourceOfIncome, String remitterMobile, String checkT24, String checkCoc, String checkAccPayee, String checkBeftn, String fileUploadedDateTime, String fileUploadedUserIp, String checkProcessed) {
        this.exCode = exCode;
        this.tranNo = tranNo;
        this.currency = currency;
        this.amount = amount;
        this.enteredDate = enteredDate;
        this.remitter = remitter;
        this.beneficiary = beneficiary;
        this.beneficiaryAccount = beneficiaryAccount;
        this.beneficiaryMobile = beneficiaryMobile;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.draweeBranchName = draweeBranchName;
        this.draweeBranchCode = draweeBranchCode;
        this.purposeOfRemittance = purposeOfRemittance;
        this.sourceOfIncome = sourceOfIncome;
        this.remitterMobile = remitterMobile;
        this.checkT24 = checkT24;
        this.checkCoc = checkCoc;
        this.checkAccPayee = checkAccPayee;
        this.checkBeftn = checkBeftn;
        this.fileUploadedDateTime = fileUploadedDateTime;
        this.fileUploadedUserIp = fileUploadedUserIp;
        this.checkProcessed = checkProcessed;
    }
}
