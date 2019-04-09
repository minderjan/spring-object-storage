package ch.zrhdev.springobjectstorage.bucket;

import com.amazonaws.services.s3.model.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BucketController {

    private static final Logger logger = LoggerFactory.getLogger(BucketController.class);

    @Autowired
    private BucketService bucketService;

    @RequestMapping(value="/buckets", method = RequestMethod.GET)
    public ResponseEntity<List<Bucket>> listBuckets() {
        return new ResponseEntity<>(bucketService.getBuckets(), HttpStatus.OK);
    }

    @RequestMapping(value="/buckets/{bucketName}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> createBucket(@PathVariable String bucketName) {
        return new ResponseEntity<>(bucketService.createBucket(bucketName), HttpStatus.OK);
    }
}
