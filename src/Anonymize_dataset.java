public class Anonymize_dataset {
    public static void main(String args[])
    {
        KAnon kanon = new KAnon();
        kanon.load("./config/kanon_rules.xml", "./data/dataset.xml");
        kanon.k_anonymize();        
    }
}
