Feature: Programatically Interact With Buckets
  # Enter feature description here

  Scenario: Bucket with unique name can be created
    Given The bucket with name "my-bucket-com-ernest-s3-test" does not exist
    When I create the bucket with name "my-bucket-com-ernest-s3-test"
    Then I can see the bucket "my-bucket-com-ernest-s3-test" exists

  Scenario: An object can be stored in a S3 Bucket
    Given I have an empty bucket with the name "com-ernest-s3-test-bucket"
    When I put the object "test-source.txt" into bucket "com-ernest-s3-test-bucket"
    Then I can see the object "test-source.txt" exists in "com-ernest-s3-test-bucket"