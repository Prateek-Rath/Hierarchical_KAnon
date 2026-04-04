# Hierarchical_KAnon
An attempt to implement k-anonymity on hierarchical data.

# Compilation

In the root folder, run the command:

```bash
javac -d . -sourcepath src:. $(find src custom -name "*.java") src/PrivacyEngine.java
```

# Running the Code

In the root folder, run the command:

```bash
java PrivacyEngine
```