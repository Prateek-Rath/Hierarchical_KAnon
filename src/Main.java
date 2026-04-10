public class Main {
    public static void main(String args[])
    {
        KAnon kanon = new KAnon();
        kanon.load("./config/rules.xml", "./data/dataset.xml");
        kanon.traverse();        
    }
}
