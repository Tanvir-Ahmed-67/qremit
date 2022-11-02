package abl.frd.qremit.controller;

import abl.frd.qremit.ResponseMessage;
import abl.frd.qremit.helper.NafexModelServiceHelper;
import abl.frd.qremit.model.NafexModel;
import abl.frd.qremit.service.NafexModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/qremit")
public class NafexModelController {

    private final NafexModelService nafexModelService;

    @Autowired
    public NafexModelController(NafexModelService nafexModelService){
        this.nafexModelService = nafexModelService;
    }

    @GetMapping(value = "/index")
    public String homePage() {
        return "nafex";
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (NafexModelServiceHelper.hasCSVFormat(file)) {
            int extensionIndex = file.getOriginalFilename().lastIndexOf(".");
            String fileNameWithoutExtension = file.getOriginalFilename().substring(0,extensionIndex);
            try {
                nafexModelService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/qremit/download/")
                        .path(fileNameWithoutExtension+".txt")
                        .toUriString();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
    }

    @GetMapping("/nafexmodels")
    public ResponseEntity<List<NafexModel>> getAllNafexModels() {
        try {
            List<NafexModel> nafexModels = nafexModelService.getAllNafexModels();

            if (nafexModels.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(nafexModels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        InputStreamResource file = new InputStreamResource(nafexModelService.load());
        int extensionIndex = fileName.lastIndexOf(".");
        String fileNameWithoutExtension = fileName.substring(0,extensionIndex);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileNameWithoutExtension+".txt")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
