package abl.frd.qremit.helper;

import abl.frd.qremit.model.NafexModel;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<NafexModel> nafexDataModelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                System.out.println("----------------------"+csvRecord.toMap());
                //NafexModel nafexDataModel = new NafexModel(
                        /*
                        csvRecord.get("Excode").replace("\"", ""),
                        csvRecord.get("Tranno").replace("\"", ""),
                        csvRecord.get("Currency").replace("\"", ""),
                        Double.parseDouble(csvRecord.get("Amount").replace("\"", "")),
                        csvRecord.get("Entered Date").replace("\"", ""),
                        csvRecord.get("Remitter").replace("\"", ""),
                        csvRecord.get("Beneficiary").replace("\"", ""),
                        csvRecord.get("Bene A/C").replace("\"", ""),
                        csvRecord.get("Bank Name").replace("\"", ""),
                        csvRecord.get("Bank Code").replace("\"", ""),
                        csvRecord.get("Branch Name").replace("\"", ""),
                        csvRecord.get("Branch Code").replace("\"", ""));

                         */
               // nafexDataModelList.add(nafexDataModel);
            }
            return nafexDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

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
}
