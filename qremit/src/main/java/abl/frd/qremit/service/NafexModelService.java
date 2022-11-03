package abl.frd.qremit.service;

import abl.frd.qremit.helper.NafexModelServiceHelper;
import abl.frd.qremit.model.ExchangeCodeMapperModel;
import abl.frd.qremit.model.NafexModel;
import abl.frd.qremit.repository.ExchangeCodeMapperModelRepository;
import abl.frd.qremit.repository.NafexModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NafexModelService {
    @Autowired
    NafexModelRepository nafexModelRepository;
    @Autowired
    ExchangeCodeMapperModelRepository exchangeCodeMapperModelRepository;

    Map<String,String> exchangeCodeMappingForService= null;
    public Map<String,String> mapExchangeCode(){
        List<ExchangeCodeMapperModel> exchangeCodeMapperModels = loadExchangeCodeMapperModel();
        Map<String,String> exchangeCodeMapping = new HashMap<String,String>();
        for(ExchangeCodeMapperModel exchangeCodeMapperModel: exchangeCodeMapperModels){
            exchangeCodeMapping.put(exchangeCodeMapperModel.getNrta(),exchangeCodeMapperModel.getExCode());
        }
        return exchangeCodeMapping;
    }
    public List<ExchangeCodeMapperModel> loadExchangeCodeMapperModel() {
        return exchangeCodeMapperModelRepository.findAll();
    }

    public String save(MultipartFile file) {
        String numberOfRows=null;
        try
        {
            List<NafexModel> nafexModels = NafexModelServiceHelper.csvToNafexModels(file.getInputStream());
            //exchangeCodeMappingForService = mapExchangeCode();
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
        System.out.println(">>>>>>>>>>>>>>>>>>>inside addDifferentModelsIntoMap() method ");
        return mappedResponseModel;
    }

    public List<NafexModel> findAllNafexModelHavingOnlineAccount() {
        System.out.println("...........findAllNafexModelHavingOnlineAccount................");
        return nafexModelRepository.findAllNafexModelHavingOnlineAccount();
    }
    public List<NafexModel> findAllNafexModelHavingCoc() {
        System.out.println("...........findAllNafexModelHavingCoc................");
        return nafexModelRepository.findAllNafexModelHavingCoc();
    }
    public List<NafexModel> findAllNafexModelHavingBeftn() {
        System.out.println("...........findAllNafexModelHavingBeftn................");
        return nafexModelRepository.findAllNafexModelHavingBeftn();
    }
    public List<NafexModel> findAllNafexModelHavingAccountPayee() {
        System.out.println("...........findAllNafexModelHavingAccountPayee................");
        return nafexModelRepository.findAllNafexModelHavingAccountPayee();
    }
}
