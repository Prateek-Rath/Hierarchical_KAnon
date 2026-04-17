# Hierarchical_KAnon
An attempt to implement k-anonymity on hierarchical data.

# Compilation

In the root folder, run the command:

```bash
javac -d ./bin -sourcepath src:. $(find src custom -name "*.java") src/Anonymize_dataset.java
javac -d ./bin -sourcepath src:. $(find src custom -name "*.java") src/RunQuery.java
```

# Running the Code

In the root folder, run the command:

```bash
java -cp ".:bin" Anonymize_dataset # run the k-anon thing, if you want to ditch it, don't run this.
java -cp ".:bin" RunQuery \
--dataFile="./data/kanon_output.xml" \
--ruleFile="./config/access_rules.xml" \
--queryXPath="/dataset/*/address" \
--userRole="MANAGER" \
--outputPath="./data/filtered-output.xml"
```


