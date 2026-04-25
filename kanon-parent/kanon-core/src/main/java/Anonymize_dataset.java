
// public class Anonymize_dataset {
//     public static void main(String args[])
//     {
//         KAnon kanon = new KAnon();
//         kanon.load("./config/kanon_rules.xml", "./data/dataset.xml");
//         kanon.k_anonymize();        
//     }
// }

public class Anonymize_dataset {

    public static void main(String[] args) {

        String rulesPath = null;
        String dataFilePath = null;
        String outputPath = null;

        // Parse arguments
        for (String arg : args) {
            if (arg.startsWith("--rules_path=")) {
                rulesPath = arg.substring("--rules_path=".length()).replaceAll("^\"|\"$", "");
            } else if (arg.startsWith("--data_file_path=")) {
                dataFilePath = arg.substring("--data_file_path=".length()).replaceAll("^\"|\"$", "");
            }
            else if(arg.startsWith("--output_path=")){
                outputPath = arg.substring("--output_path=".length()).replaceAll("^\"|\"$", "");
            }
        }

        // Validate inputs
        if (rulesPath == null || dataFilePath == null) {
            System.err.println("Usage: java -jar <jar-file> --rules_path=\"path/to/rules.xml\" --data_file_path=\"path/to/data.xml\"");
            System.exit(1);
        }

        // Run K-Anonymity
        KAnon kanon = new KAnon();
        kanon.load(rulesPath, dataFilePath);
        kanon.k_anonymize(outputPath);
    }
}