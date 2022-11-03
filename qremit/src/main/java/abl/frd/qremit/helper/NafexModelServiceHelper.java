package abl.frd.qremit.helper;

import abl.frd.qremit.model.NafexModel;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NafexModelServiceHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Excode","Tranno","Currency","Amount","Entered Date","Remitter","Beneficiary","Bene A/C","Bank Name","Bank Code","Branch Name","Branch Code"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("text/plain")) {
            return true;
        }
        return false;
    }
    public static List<NafexModel> csvToNafexModels(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withIgnoreHeaderCase().withTrim());) {
            List<NafexModel> nafexDataModelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                String singleLineOfData = csvRecord.get(0);
                String[] columnNumber = singleLineOfData.split("\\|");
                NafexModel nafexDataModel = new NafexModel(
                        columnNumber[0].toString(), //exCode
                        columnNumber[1].toString(), //Tranno
                        columnNumber[2].toString(), //Currency
                        Double.parseDouble(columnNumber[3].toString()), //Amount
                        columnNumber[4].toString(), //enteredDate
                        columnNumber[5].toString(), //remitter

                        columnNumber[6].toString(), // beneficiary
                        columnNumber[7].toString(), //beneficiaryAccount
                        columnNumber[12].toString(), //beneficiaryMobile
                        columnNumber[8].toString(), //bankName
                        columnNumber[9].toString(), //bankCode
                        columnNumber[10].toString(), //branchName
                        columnNumber[11].toString(), // branchCode

                        columnNumber[13].toString(), //draweeBranchName
                        columnNumber[14].toString(), //draweeBranchCode
                        columnNumber[15].toString(), //purposeOfRemittance
                        columnNumber[16].toString(), //sourceOfIncome
                        columnNumber[17].toString(), //remitterMobile

                        putOnlineFlag(columnNumber[7].toString()), // checkT24
                        putCocFlag(columnNumber[7].toString()), //checkCoc
                        "0", //checkAccPayee
                        putBeftnFlag(columnNumber[8].toString()), //checkBeftn
                        "0", //fileUploadedDateTime
                        "0", //fileUploadedUserIp
                        "0"); //checkProcessed

                nafexDataModelList.add(nafexDataModel);
            }
            return nafexDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    // Need to implement this method
    public static ByteArrayInputStream nafexModelToCSV(List<NafexModel> nafexDataModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (NafexModel nafexDataModel : nafexDataModelList) {
                List<Object> data = Arrays.asList(
                        nafexDataModel.getTranNo(),
                        "CRED",
                        nafexDataModel.getEnteredDate(),
                        nafexDataModel.getCurrency(),
                        nafexDataModel.getAmount(),
                        nafexDataModel.getRemitter(),
                        nafexDataModel.getExCode(),
                        nafexDataModel.getBankName(),
                        nafexDataModel.getBranchName(),
                        null,
                        nafexDataModel.getBeneficiaryAccount(),
                        nafexDataModel.getBeneficiary(),
                        null,
                        null,
                        //apiModel.getBankCode(),
                        "4006",
                        //apiModel.getBranchCode(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

    public static String putCocFlag(String accountNumber){
        if(isOnlineAccoutNumberFound(accountNumber)){
            return "0";
        }
        else{
            if(accountNumber.contains("coc") || accountNumber.contains("COC") ){
                return "1";
            }
            else {
                return "0";
            }
        }
    }
    public static String getOnlineAccountNumber(String accountNumber){
        //^.*02000(\d{8}).*$
        Pattern p = Pattern.compile("^.*02000(\\d{8}).*$");
        Matcher m = p.matcher(accountNumber);
        String onlineAccountNumber=null;
        if (m.find())
        {
            onlineAccountNumber = m.group(1);
        }
        return onlineAccountNumber;
    }
    public static String putOnlineFlag(String accountNumber){
        if(isOnlineAccoutNumberFound(accountNumber)){

            return "1";
        }
        else{
            return "0";
        }
    }
    public static boolean isOnlineAccoutNumberFound(String accountNumber){
        Pattern p = Pattern.compile("^.*02000(\\d{8}).*$");
        Matcher m = p.matcher(accountNumber);
        if (m.find())
        {
            return true;
        }
        else{
            return false;
        }
    }


    public static String putBeftnFlag(String bankName){
        if(bankName.contains("AGRANI") || bankName.contains("agrani")|| bankName.contains("Agrani") || bankName.contains("abl") || bankName.contains("Abl") || bankName.contains("ABL")){
            return "0";
        }
        else{
            return "1";
        }
    }

    // truncate table nafex_data_table;
}
