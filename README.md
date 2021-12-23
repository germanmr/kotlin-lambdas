# lambda-kotlin-example

Reference:
Sample code for the "Kotlin and Groovy JVM Languages with AWS Lambda" blog post. Shows how to deploy AWS Lambda functions using Kotlin and Groovy JVM languages.

In this repository we can version our lambda functions

# Quick Start

## Requirements

Before cloning and building these examples, you'll need to install the following dependencies:
* Java JDK 8 ([link](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html))

## Clone repo

Clone the Git repository

```bash
git clone https://github.com/awslabs/lambda-kotlin-groovy-example
cd lambda-kotlin-groovy-example
```

## Build Kotlin Lambda function

Execute the following commands to build the Kotlin example AWS Lambda function. Note, this command uses Gradle (via the Gradle Wrapper) to download dependencies and build the fat JAR file containing the AWS Lambda function.

```bash
cd kotlin
./gradlew shadowjar
```

Once Gradle finished. The fat JAR will be available at `build/libs/jvmlangs-kotlin-1.0-SNAPSHOT-all.jar`. This JAR file can be deployed using AWS Lambda.

Lambda creation

aws lambda create-function --region us-east-2 --function-name db-instance-scheduler --zip-file fileb://build/libs/jvmlangs-kotlin-1.0-SNAPSHOT-all.jar --role arn:aws:iam::123456789879:role/a_role_that_can_create_lambdas --handler com.aws.blog.jvmlangs.kotlin.Main::handler --runtime java8 --timeout 15 --memory-size 512

Important:

--role 
    Copy role arn from AWS 

--memory-size
    Should be at least 256 for the lambda to be able to spin up before a timeout occurs

