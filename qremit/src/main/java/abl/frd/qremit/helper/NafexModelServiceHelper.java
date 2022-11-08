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
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withIgnoreHeaderCase().withTrim())) {
            List<NafexModel> nafexDataModelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                NafexModel nafexDataModel = new NafexModel(
                        csvRecord.get(0), //exCode
                        csvRecord.get(1), //Tranno
                        csvRecord.get(2), //Currency
                        Double.parseDouble(csvRecord.get(3)), //Amount
                        csvRecord.get(4), //enteredDate
                        csvRecord.get(5), //remitter

                        csvRecord.get(6), // beneficiary
                        csvRecord.get(7), //beneficiaryAccount
                        csvRecord.get(12), //beneficiaryMobile
                        csvRecord.get(8), //bankName
                        csvRecord.get(9), //bankCode
                        csvRecord.get(10), //branchName
                        csvRecord.get(11), // branchCode

                        csvRecord.get(13), //draweeBranchName
                        csvRecord.get(14), //draweeBranchCode
                        csvRecord.get(15), //purposeOfRemittance
                        csvRecord.get(16), //sourceOfIncome
                        csvRecord.get(17), //remitterMobile

                        putOnlineFlag(csvRecord.get(7).trim()), // checkT24
                        putCocFlag(csvRecord.get(7).trim()), //checkCoc
                        "0", //checkAccPayee
                        putBeftnFlag(csvRecord.get(8).trim()), //checkBeftn
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
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
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


    public static ByteArrayInputStream generateTextFileForNafexModelHavingOnlineAccount(List<NafexModel> nafexDataModelListHavingOnlineAccount) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (NafexModel nafexDataModel : nafexDataModelListHavingOnlineAccount) {
                nafexDataModel.setBeneficiaryAccount(getOnlineAccountNumber(nafexDataModel.getBeneficiaryAccount()));
                List<Object> data = Arrays.asList(
                        nafexDataModel.getTranNo(),
                        nafexDataModel.getExCode(),
                        nafexDataModel.getBeneficiaryAccount(),
                        nafexDataModel.getBeneficiary(),
                        nafexDataModel.getRemitter(),
                        nafexDataModel.getAmount()
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("failed to generate online text file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream generateTextFileForNafexModelHavingCoc(List<NafexModel> nafexDataModelListHavingCoc) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (NafexModel nafexDataModel : nafexDataModelListHavingCoc) {
                List<Object> data = Arrays.asList(
                        nafexDataModel.getTranNo(),
                        "CRED",
                        nafexDataModel.getEnteredDate(),
                        nafexDataModel.getCurrency(),
                        nafexDataModel.getAmount(),
                        nafexDataModel.getBeneficiary(),
                        nafexDataModel.getExCode(),
                        nafexDataModel.getBankName(),
                        nafexDataModel.getBranchName(),
                        null,
                        nafexDataModel.getBeneficiaryAccount(),
                        nafexDataModel.getRemitter(),
                        null,
                        null,
                        "4006",
                        "PRINCIPAL BRANCH",
                        "PRINCIPAL CORP.BR.",
                        "22",
                        "1",
                        "incentivre",    // have to implement. It should be variable. So read it from properties file
                        "5"
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("failed to generate coc text file: " + e.getMessage());
        }
    }
    public static ByteArrayInputStream generateTextFileForNafexModelHavingAccountPayee(List<NafexModel> nafexDataModelListHavingAccountPayee) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (NafexModel nafexDataModel : nafexDataModelListHavingAccountPayee) {
                nafexDataModel.setBeneficiaryAccount(getOnlineAccountNumber(nafexDataModel.getBeneficiaryAccount()));
                List<Object> data = Arrays.asList(
                        nafexDataModel.getTranNo(),
                        "CRED",
                        nafexDataModel.getEnteredDate(),
                        nafexDataModel.getCurrency(),
                        nafexDataModel.getAmount(),
                        nafexDataModel.getBeneficiary(),
                        nafexDataModel.getExCode(),
                        nafexDataModel.getBankName(),
                        nafexDataModel.getBranchName(),
                        null,
                        nafexDataModel.getBeneficiaryAccount(),
                        nafexDataModel.getRemitter(),
                        null,
                        null,
                        "4006",
                        "PRINCIPAL BRANCH",
                        "PRINCIPAL CORP.BR.",
                        "22",
                        "1",
                        "incentivre",    // have to implement. It should be variable. So read it from properties file
                        "15"
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("failed to generate coc text file: " + e.getMessage());
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
        //^.*02000(\d{8})$.*
        Pattern p = Pattern.compile("^.*02000(\\d{8})$.*");
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
        System.out.println(accountNumber);
        Pattern p = Pattern.compile("^.*02000(\\d{8})$.*");
        Matcher m = p.matcher(accountNumber);
        if (m.find())
        {
            System.out.println("T24 Account No Found -"+accountNumber);
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
