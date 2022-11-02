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

    public void save(MultipartFile file) {
        try
        {
            List<NafexModel> nafexModels = NafexModelServiceHelper.csvToNafexModels(file.getInputStream());
            exchangeCodeMappingForService = mapExchangeCode();
            for(NafexModel nafexModel : nafexModels){
                nafexModel.setExCode(exchangeCodeMappingForService.get(nafexModel.getExCode()).trim());
            }
            nafexModelRepository.saveAll(nafexModels);
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

}
