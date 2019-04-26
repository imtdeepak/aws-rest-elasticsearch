# aws-rest-elasticsearch
aws-rest-elasticsearch lambda project in Java 
# Problem Description
Using Java, write a microservice that invokes AWS elastic search and make it available using API gateway.  

1. Test Data - http://askebsa.dol.gov/FOIA%20Files/2017/Latest/F_5500_2017_Latest.zip
2. Search should be allowed by Plan name, Sponsor name and Sponsor State

## This problem has two parts. 
1. Loading the data to elastic search

I am using logstash and uploaded the data first. 

### Install the logstash
```
brew install logstash
```
### Create the config file which will be used to upload to logstash like logstash.config

### Load the data using command line
```
logstash -f logstash.config
```
This process takes time and depends on csv file size and network speed.

2. Now the data is loaded and we need to search this 
For this I have created a Java Lambda and which is being invoked by Amazon API Gateway.

For creating the lambda function I used the aws lambda plugin for eclipse which creates the basic project with the test framework with TestContext and TestUtils already created. 

### Command to test this
```
curl -X POST \
  https://blab4pa33l.execute-api.us-east-2.amazonaws.com/prod/plansponsorelastic \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 1fb66262-fa5a-4923-8b1f-55cf60bfb640' \
  -H 'cache-control: no-cache' \
  -d '{
	"sponsorState":"NY"
}'
```
