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
