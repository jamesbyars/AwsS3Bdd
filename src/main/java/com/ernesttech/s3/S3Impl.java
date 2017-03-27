package com.ernesttech.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 3/27/17.
 */
public class S3Impl {

    private AWSCredentials awsCredentials;
    private AmazonS3 amazonS3Client;

    public S3Impl() {
        awsCredentials = getCredentials();
        amazonS3Client = createS3ClientFromCredentials(awsCredentials);
    }

    public void deleteObjectInBucket(final String bucketName, final String objectKey) {
        amazonS3Client.deleteObject(bucketName, objectKey);
    }

    public List<String> listObjectsInBucket(final String bucketName) {

        List<String> objectKeys = new ArrayList<String>();

        ObjectListing objectListing = amazonS3Client.listObjects(
                new ListObjectsRequest()
                        .withBucketName(bucketName));

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            objectKeys.add(objectSummary.getKey());
        }

        return objectKeys;
    }

    public void putFileIntoBucket(final String bucketName, final String key, final File fileToPut) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, key, fileToPut));
    }

    public void deleteBucketWithName(final String bucketName) throws InterruptedException {
        amazonS3Client.deleteBucket(bucketName);
    }

    public void createBucketWithName(final String bucketName) {

        amazonS3Client.createBucket(bucketName);

    }

    public List<String> listBuckets() {
        List<String> bucketNames = new ArrayList<String>();

        for (Bucket bucket : amazonS3Client.listBuckets()) {
            bucketNames.add(bucket.getName());
        }

        return bucketNames;
    }


    private AmazonS3 createS3ClientFromCredentials(final AWSCredentials credentials) {
        AmazonS3 s3 = new AmazonS3Client(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);

        return s3;
    }

    /**
     * Gets credentials
     *
     * @return {@link AWSCredentials}
     */
    private AWSCredentials getCredentials() {
        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (~/.aws/credentials).
         */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        return credentials;
    }


}
