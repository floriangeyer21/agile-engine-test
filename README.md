# agile-engine-test

 For build this project, need to be installed Maven on your computer.
 From project directory execute "mvn clean package". Then move to "target" directory and execute "java -jar demo-0.0.1-SNAPSHOT.jar".
 After API is started, the next endpoint are will be available:
  http://localhost:8080/photos/ - retrieve all available data from the database,
  http://localhost:8080/photos/${id} - retrieve photo by the particular id,
  http://localhost:8080/photos/pages?page= - after "=" you must pointed number of page with you wnat to retrieve,
  http://localhost:8080/photos/search? - after "?" sign you can write the category which you want to use for searching, the next category are available "author", "externalId", "camera", "croppedPicture", "fullPicture",
  
 You can define next properties in application.properties file:
  renewal.cache.time - time to update cached data, 60000 = 1 minute,
  spring.data.cassandra.request.page-size - for specify page size,
  apikey - for defining apiKey needed for retrieve bearer token.

