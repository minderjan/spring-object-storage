package ch.zrhdev.springobjectstorage.bucket;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {

    private static final Logger logger = LoggerFactory.getLogger(BucketService.class);

    @Autowired
    @Qualifier("s3client")
    private AmazonS3 s3Client;

    /**
     * List all Buckets based on your credentials.
     *
     * @return Java List with Amazon Bucket Objecst in it. If null, no Buckets were found.
     */
    public List<Bucket> getBuckets() {

        List<Bucket> bucketList = null;

        try {
            logger.debug("Try to get all buckets");
            bucketList = s3Client.listBuckets();
        } catch (Exception e) {
            logger.error("Failed to list buckets: {}", e.getMessage());
        }

        if (bucketList != null) {
            logger.debug("Buckets found: {}", bucketList.size());
        }

        return bucketList;
    }

    /**
     * Create a new Bucket on S3 Storage.
     * @param bucketName
     * @return Bucket Location as String if successfully created. Otherwise null will be returned.
     */
    public Boolean createBucket(String bucketName) {

        try {
            // Check if Bucket exists
            if (!s3Client.doesBucketExist(bucketName)) {
                //s3Client.createBucket(new CreateBucketRequest(bucketName));

                // Verify that the bucket was created by retrieving it and checking its location.
                s3Client.createBucket(bucketName);
                logger.debug("Bucket with name '{}' created", bucketName);
                return true;
            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it and returned an error response.
           logger.error("Failed to create Bucket '{}' because of an aws exception: {}", bucketName, e.getErrorMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error("Failed to create Bucket '{}' because of an sdk exception: {}", bucketName, e.getMessage());
        }
        return false;
    }

}
