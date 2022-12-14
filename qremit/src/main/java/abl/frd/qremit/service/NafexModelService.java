package abl.frd.qremit.service;

import abl.frd.qremit.helper.NafexModelServiceHelper;
import abl.frd.qremit.model.NafexModel;
import abl.frd.qremit.repository.NafexModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class NafexModelService {
    @Autowired
    NafexModelRepository nafexModelRepository;
    public String save(MultipartFile file) {
        String numberOfRows=null;
        try
        {
            List<NafexModel> nafexModels = NafexModelServiceHelper.csvToNafexModels(file.getInputStream());
            for(NafexModel nafexModel : nafexModels){
                nafexModel.setExCode("7010234");
            }
            nafexModelRepository.saveAll(nafexModels);
            numberOfRows = String.valueOf(nafexModelRepository.count());
            return numberOfRows;
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<NafexModel> nafexModels = nafexModelRepository.findAll();
        ByteArrayInputStream in = NafexModelServiceHelper.nafexModelToCSV(nafexModels);
        return in;
    }

    public List<NafexModel> getAllNafexModels() {
        return nafexModelRepository.findAll();
    }


    public Map<String, List<NafexModel>> addDifferentModelsIntoMap(List<NafexModel> nafexModelHavingOnlineAccount, List<NafexModel> nafexModelHavingCoc, List<NafexModel> nafexModelHavingBeftn, List<NafexModel> nafexModelHavingAccountPayee){
        Map<String, List<NafexModel>> mappedResponseModel = null;
        mappedResponseModel.put("online", nafexModelHavingOnlineAccount);
        mappedResponseModel.put("coc", nafexModelHavingCoc);
        mappedResponseModel.put("beftn", nafexModelHavingBeftn);
        mappedResponseModel.put("accountPayee", nafexModelHavingAccountPayee);
        return mappedResponseModel;
    }

    public ByteArrayInputStream findAllNafexModelHavingOnlineAccount() {
        List<NafexModel> allModelsHavingOnline = nafexModelRepository.findAllNafexModelHavingOnlineAccount();
        System.out.println("allModelsHavingOnline: "+allModelsHavingOnline.toString());
        ByteArrayInputStream in = NafexModelServiceHelper.generateTextFileForNafexModelHavingOnlineAccount(allModelsHavingOnline);
        return in;
    }
    public ByteArrayInputStream findAllNafexModelHavingCoc() {
        List<NafexModel> allModelsHavingCoc = nafexModelRepository.findAllNafexModelHavingCoc();
        System.out.println("allModelsHavingCoc: "+allModelsHavingCoc.toString());
        ByteArrayInputStream in = NafexModelServiceHelper.generateTextFileForNafexModelHavingCoc(allModelsHavingCoc);
        return in;
    }
    public ByteArrayInputStream findAllNafexModelHavingAccountPayee() {
        List<NafexModel> allModelsHavingAccountPayee = nafexModelRepository.findAllNafexModelHavingAccountPayee();
        ByteArrayInputStream in = NafexModelServiceHelper.generateTextFileForNafexModelHavingAccountPayee(allModelsHavingAccountPayee);
        return in;
    }
    public List<NafexModel> findAllNafexModelHavingBeftn() {
        return nafexModelRepository.findAllNafexModelHavingAccountPayee();
    }
}
