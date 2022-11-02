package abl.frd.qremit.model;

import javax.persistence.*;

@Entity
@Table(name = "qvoucherinfo")
public class ExchangeCodeMapperModel {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name = "exName")
    private String exName;
    @Column(name = "nrta")
    private String nrta;
    @Column(name = "exCode")
    private String exCode;


    public ExchangeCodeMapperModel(){

    }

    public ExchangeCodeMapperModel(int id, String exName, String nrta, String exCode) {
        this.id = id;
        this.exName = exName;
        this.nrta = nrta;
        this.exCode = exCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getNrta() {
        return nrta;
    }

    public void setNrta(String nrta) {
        this.nrta = nrta;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    @Override
    public String toString(){
        return "ExCode [id=" + id + ", exName=" + exName + ", nrta=" + nrta + ", exCode=" + exCode +"]";
    }
}
