package com.ernesttech.s3.steps;

import com.ernesttech.s3.S3Impl;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.File;
import java.util.List;

/**
 * Created by james on 3/27/17.
 */
public class BucketInteractionStepDefinitions {

    private S3Impl s3Impl;

    @Before
    public void before() {
        s3Impl = new S3Impl();
    }

    @Given("^The bucket with name \"([^\"]*)\" does not exist$")
    public void the_bucket_with_name_does_not_exist(String arg1) throws Throwable {
        List<String> bucketNames = s3Impl.listBuckets();

        if (bucketNames.contains(arg1)) {
            s3Impl.deleteBucketWithName(arg1);
            bucketNames = s3Impl.listBuckets();
        }

        Assert.assertFalse(bucketNames.contains(arg1));
    }

    @When("^I create the bucket with name \"([^\"]*)\"$")
    public void i_create_the_bucket_with_name(String arg1) throws Throwable {
        s3Impl.createBucketWithName(arg1);
    }

    @Then("^I can see the bucket \"([^\"]*)\" exists$")
    public void i_can_see_the_bucket_exists(String arg1) throws Throwable {
        List<String> bucketNames = s3Impl.listBuckets();
        Assert.assertTrue(bucketNames.contains(arg1));
    }



    /////////////



    @Given("^I have an empty bucket with the name \"([^\"]*)\"$")
    public void i_have_an_empty_bucket_with_the_name(String bucketName) throws Throwable {
        List<String> bucketNames = s3Impl.listBuckets();

        if (!bucketNames.contains(bucketName)) {
            s3Impl.createBucketWithName(bucketName);
            bucketNames = s3Impl.listBuckets();
        } else {
            List<String> bucketContents = s3Impl.listObjectsInBucket(bucketName);
            for (String key : bucketContents) {
                s3Impl.deleteObjectInBucket(bucketName, key);
            }
        }

        Assert.assertTrue(bucketNames.contains(bucketName));
        List<String> objectKeys = s3Impl.listObjectsInBucket(bucketName);
        Assert.assertTrue(objectKeys.size() == 0);
    }

    @When("^I put the object \"([^\"]*)\" into bucket \"([^\"]*)\"$")
    public void i_put_the_object_into_bucket(String filename, String bucketName) throws Throwable {
        String[] parts = filename.split("\\.");

        File file = File.createTempFile(parts[0], parts[1]);
        file.deleteOnExit();

        s3Impl.putFileIntoBucket(bucketName, filename, file);

    }

    @Then("^I can see the object \"([^\"]*)\" exists in \"([^\"]*)\"$")
    public void i_can_see_the_object_exists_in(String objectKey, String bucketName) throws Throwable {
        List<String> objectKeys = s3Impl.listObjectsInBucket(bucketName);

        Assert.assertTrue(objectKeys.contains(objectKey));
    }

}
