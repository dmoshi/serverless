
### SERVERLESS ARCHITECTURE FOR CONTACT ME SECTION OF MY WEBSITE

#### _(A) ARCHITECTURE DIAGRAM_

<br>

![architecture](https://raw.githubusercontent.com/dmoshi/serverless/master/dmoshi.com.contactme.lambda/src/main/resources/architecture_contactme_transparent.jpg "architecture")

<br>

#### _(B) BUILDING AND DEPLOYONG THE ABOVE ARCHITECTURE_

1. What you will need :- 

   * Maven installed.
   * AWS CLI and AWS SAM CLI installed.
   * AWS credentials with necessary permissions to create and update Lambda, IAM Role, SNS Topic and Subscription, CloudFormation Stack creation and updates. 
   * Credentials added to path **{user_home}/.aws/credentials** on mac, unix or windows. 
   * An AWS S3 bucket that will be used to store Lambda function binaries. 
   * Steps to do the above steps can easily be found online.
 
 2. To build and deploy using aws sam cli perform the following commands at the root folder of this codebase :- 
 
   ~~~
   mvn clean package
   ~~~
   _(You may want to skip tests by adding argument -DskipTests or edit the unit test file and put your SNS ARN and REGION to avoid build failure)_ 
  
  <br>
 
   ~~~
   sam package   --template-file serverless.yml   --output-template-file package.yml   --s3-bucket {your_s3_package} 
   ~~~
   
   Then,
   
   ~~~
   sam deploy   --template-file package.yml   --stack-name {any_name_of_choice}   --capabilities CAPABILITY_IAM
   ~~~

<br>

The above commands will create an AWS CloudFormation stack with the following resources on your AWS environment :- 

* IAM Role with permissions to publish messages to an SNS topic, this will be used by the lambda function.
* One SNS Topic
* One SNS Subscription bound to the above topic, protocol is email with email address specified in serverless.yml 
* An AWS Lambda function with a binding to the IAM role above
* An AWS API Gateway of type LAMBDA_PROXY which will trigger the above function over HTTP POST 



#### _(C) OTHER KEY CONFIGURATIONS ON AWS_ 

<br>

> * Static website for dmoshi.com is hosted on S3 as static website with content served via a CloudFront distributiton (CDN).
> * The custom domain dmoshi.com is hosted on Route53 and points to the cloud distribution.
> * The Cloudfront distribution is configured to serve content over HTTPS using a free AWS Cerficate Manager (ACM) as long as you own your domain.

<br>





