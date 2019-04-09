package ch.zrhdev.springobjectstorage.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileContentStore fileContentStore;

    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(value="/files", method = RequestMethod.POST)
    public ResponseEntity<?> saveFile(@RequestParam("file") MultipartFile file) throws IOException {

        // Initialize a empty File
        File i = new File();

        // Set Meta Information from File
        i.setMimeType(file.getContentType());
        i.setName(file.getOriginalFilename());
        i.setBucket("my-storage");

        // save file in the storage layer
        fileContentStore.setContent(i, file.getInputStream());

        // save updated content-related info
        fileRepository.save(i);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/files/{fileId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFile(@PathVariable("fileId") Long id, @RequestParam("file") MultipartFile file) throws IOException {

        // Get File Infos from DB
        File i = fileRepository.getOne(id);

        // Set new File Infos
        i.setMimeType(file.getContentType());
        i.setName(file.getOriginalFilename());
        i.setBucket("tsq-storage");

        // save file in the storage layer
        fileContentStore.setContent(i, file.getInputStream());

        // save updated content-related info
        fileRepository.save(i);

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value="/files/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<?> getFile(@PathVariable("fileId") Long id) throws IOException {

        // Get File Infos from DB
        File f = fileRepository.getOne(id);

        // Get File from Content Store
        InputStreamResource inputStreamResource = new InputStreamResource(fileContentStore.getContent(f));

        // Prepare File name to make use in Header
        String fileName = URLEncoder.encode(f.getName(), "UTF-8");
        fileName = URLDecoder.decode(fileName, "ISO8859_1");

        // Set HTTP Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(f.getContentLength());
        headers.set("Content-Type", f.getMimeType());
        headers.set("Content-disposition", "attachment; filename="+ fileName);

        return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
    }

    @RequestMapping(value="/files", method = RequestMethod.GET)
    public ResponseEntity<List<File>> getFileList() {

        // Get all Files from DB
        List<File> imgList = fileRepository.findAll();

        return new ResponseEntity<>(imgList, HttpStatus.OK);
    }

    @RequestMapping(value="/files/{fileId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFile(@PathVariable("fileId") Long id) throws IOException {
        // Get File Infos from DB
        File i = fileRepository.getOne(id);
        // Delete File from Content Storage
        fileContentStore.unsetContent(i);
        // Delete File Infos from Database
        fileRepository.delete(i);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
