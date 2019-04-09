# Spring Content Storage with Cloud Foundry

![Spring Content Architecture](https://paulcwarren.github.io/spring-content/spring-content-arch.jpg)

https://paulcwarren.github.io/spring-content/spring-content-arch.jpg

## Dependencies
```
compile("com.github.paulcwarren:content-s3-spring-boot-starter:0.0.11")
compile('com.github.paulcwarren:spring-content-fs-boot-starter:0.0.11')
compile("org.springframework.boot:spring-boot-starter-data-jpa")
```

## Developer Tools
### AWS CLI on Cloud Foundry 
Push AWS Cli Docker Image to Cloud
```
cf push aws-cli --docker-image fstab/aws-cli
cf ssh aws-cli
```
#### Configure AWS Cli (region should be left blank)
```
aws configure
```
#### Manage Buckets
```
// List Buckets
aws s3api list-buckets --endpoint-url https://xy.api.com

// Create Bucket
aws s3api create-bucket --bucket my-bucket --endpoint-url https://xy.api.com

// Delete Bucket
aws s3api delete-bucket --bucket my-bucket --endpoint-url https://xy.api.com

// List Bucket Content
aws s3 ls s3://my-bucket --endpoint-url https://xy.api.com
```
