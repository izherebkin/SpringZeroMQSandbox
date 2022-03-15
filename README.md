# Spring ZeroMQ Sandbox
Spring ZeroMQ Sandbox is a Spring Boot application that utilized [ZeroMQ](https://zeromq.org/) Pub-Sub messaging pattern.

## Features
* Each instance of application can be as publisher (PUB), subscriber (SUB) or publisher and subscriber (PUB-SUB). It configures in `application.properties`
* Subscriber consumes messages from 1 to N publishers
* Point-to-point security via [CurveZMQ](http://curvezmq.org/)

## Run
Run an application using Maven:

    mvn clean spring-boot:run

## Build
Build an application using Maven:

    mvn clean package

After a successful build, the jar-file with dependencies will be located in the `target` directory

## License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```