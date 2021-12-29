### RUNNING WITH PROFILES

**Running the project from Intellij Idea Community Edition IDE with a specific profile:**  
The example is for local profile, check "Profiles" class for existing profiles
 
- Create new Gradle Run/Debug configuration
- Name: `  bootRun local`
- Run: ` bootRun` 
- Choose Gradle Project:`  launches-backend-kotlin-experimental`
- Environment variables: ` "spring.profiles.active=local" `

### DOCKER

Application can be dockerized: project folder contains Dockerfile.

Following environment variables are specified in Dockerfile and can be overridden:
1. Spring profile. By default, is set to prod.

   `   SPRING_PROFILES_ACTIVE=prod`

2. Port to run application. By default, is set to 8080

   `   SERVER_PORT=8080`
   
3. Name of jar file. By default, is the same as in settings.gradle: launches-backend-kotlin-experimental-0.0.1-SNAPSHOT.jar

   `APP_JAR="launches-backend-kotlin-experimental-0.0.1-SNAPSHOT.jar"`

##### DOCKER PLUGIN

Building docker image and creating container can be done with help of Docker plugin for Gradle.
1. Create an image: `gradle docker`
2. Run container: `gradle dockerRun`
3. Stop container: `gradle dockerStop`
4. Remove container: `gradle dockerRemoveContainer`

**Tip**: Building an application, creating image and running a container can be done with only one command:
   `gradle build docker dockerRun`

### Testing our with in-progress branch with deploy ( distinct from "main" or "develop"):
When we want to test that our "been developed" branch is functional in ECS
- Tell the rest of the developers that you're doing this ;)
- Go to .gitlab-ci.yml
- Comment the lines:
```
   only:
   - main
   - develop
```
- Commit and push  

This fires the whole CI/CD pipeline for your current branch
