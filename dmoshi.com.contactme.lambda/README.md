
### SERVERLESS ARCHITECTURE FOR CONTACT ME SECTION OF MY WEBSITE

#### _ARCHITECTURE DIAGRAM_

<br>

![architecture](https://raw.githubusercontent.com/dmoshi/serverless/master/dmoshi.com.contactme.lambda/src/main/resources/architecture_contactme_transparent.jpg "architecture")

<br>

#### _BUILDING AND DEPLOYONG THE ABOVE ARCHITECTURE_

1. What you will need :- 

<br>

#### _KEY CONFIGURATIONS ON AWS_ 

<br>

> * Static website for dmoshi.com is hosted on S3 as static website with content served via a CloudFront distributiton (CDN).
> * The custom domain dmoshi.com is hosted on Route53 and points to the cloud distribution.
> * The Cloudfront distribution is configured to serve content over HTTPS using a free AWS Cerficate Manager (ACM) as long as you own your domain.

<br>





