
## Lambda Function for **Contact Me** section of my website [dmoshi.com](https://dmoshi.com)

### Architecture

![architecture](https://raw.githubusercontent.com/dmoshi/serverless/master/dmoshi.com.contactme.lambda/src/main/resources/architecture_contactme_transparent.jpg "architecture")

### Key configurations on AWS 

~~~~
> Static website for dmoshi.com is hosted on S3 as static website with content served via a CloudFront distributiton (CDN).
> The custom domain dmoshi.com is hosted on Route53 and points to the cloud distribution.
> The Cloudfront distribution is configured to serve content over HTTPS using a free AWS Cerficate Manager (ACM) as long as you own your domain.
~~~~

### Building and deploying the above architecture

