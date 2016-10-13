# CapitalOneExercise

#What has been done:
For now, only getAllTasactions is implemented. Improvements will be done over the weekend.
Created console based Java application that read commands from console and print back the results to the console.


#How to run:
To run use the following command: (note this jar is built using jdk1.8)
java -jar CapitalOneExercise-1.jar 

#How to rebuild:
clone https://github.com/bcherra/CapitalOneExercise/new/InitialCheckin
Change pom.xml to correct jdk version.
  <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.5.1</version>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
          
        </configuration>
        
      </plugin>
      
      TO
      
       <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.5.1</version>
        <configuration>
          <source>1.7</source>
          <target>1.7</target>
          
        </configuration>
        
      </plugin>
One base folder run command : mvn clean install
