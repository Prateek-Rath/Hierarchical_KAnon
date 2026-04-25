# Hierarchical_KAnon
An attempt to implement k-anonymity on hierarchical data.

# Compilation
From your project's root directory run:
mvn clean install # to get the jar of your own project

In case you're following our structure of kanon-plugin directory, go into the kanon-plugin directory and run
mvn clean install

# Anonymize the dataset
You are given the kanon-core-1.0-SNAPSHOT.jar file.
Say it is in the path:
```path_to_core_jar/kanon-core-1.0-SNAPSHOT.jar```
In our case this is ```/kanon-core-1.0-SNAPSHOt.jar```

From  your project's root directory run
```
java -cp "path_to_core_jar/kanon-core-1.0-SNAPSHOT.jar:target/kanon-plugin-1.0-SNAPSHOT.jar" Anonymize_dataset \
  --rules_path="src/main/resources/config/kanon_rules.xml" \
  --data_file_path="src/main/resources/data/dataset.xml"
  --output_path="path_to_output/kanon_dataset.xml
```

In our case this is
```
java -cp "./kanon-core-1.0-SNAPSHOT.jar:target/kanon-plugin-1.0-SNAPSHOT.jar" Anonymize_dataset \
  --rules_path="src/main/resources/config/kanon_rules.xml" \
  --data_file_path="src/main/resources/data/dataset.xml" \
  --output_path="src/main/resources/data/kanon_dataset.xml"
```
# Run a query
From your project's root directory run
```
java -cp "path_to_core_jar/kanon-core-1.0-SNAPSHOT.jar:target/kanon-plugin-1.0-SNAPSHOT.jar" RunQuery \
  --dataFile="src/main/resources/data/kanon_dataset.xml" \
  --ruleFile="src/main/resources/config/access_rules.xml" \
--outputPath="path_to_output/query_output.xml" \
--userRole="MANAGER" \
--queryXPath="/dataset/*/address"
```

In our case this is
```
java -cp "./kanon-core-1.0-SNAPSHOT.jar:target/kanon-plugin-1.0-SNAPSHOT.jar" RunQuery \
  --dataFile="src/main/resources/data/kanon_dataset.xml" \
  --ruleFile="src/main/resources/config/access_rules.xml" \
--outputPath="src/main/resources/data/query_output.xml" \
--userRole="MANAGER" \
--queryXPath="/dataset/*/address"
```

